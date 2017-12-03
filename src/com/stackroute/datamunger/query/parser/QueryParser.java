package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * this method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {

		if (!queryString.isEmpty()) {

			queryParameter.setFile(getFileName(replaceCharacters(queryString)));
			getBaseQuery(replaceCharacters(queryString));
			if (queryString.contains("where")) {
				queryParameter.setRestrictions(getConditions(replaceCharacters(queryString)));
				queryParameter.setLogicalOperators(getLogicalOperators(replaceCharacters(queryString)));
			}

			getFields(replaceCharacters(queryString));
			if (queryString.contains(" order by")) {
				queryParameter.setOrderByFields(getOrderByFields(replaceCharacters(queryString)));
			}
			if (queryString.contains(" group by")) {
				queryParameter.setGroupByFields(getGroupByFields(replaceCharacters(queryString)));
			}
			if (queryString.contains("(")) {
				queryParameter.setAggregateFunctions(getAggregateFunctions(replaceCharacters(queryString)));
			}

		} else {
			System.out.println("Query String is empty!!s");
		}
		return queryParameter;

	}

	/*
	 * this methods will add white spaces into query
	 */

	public String replaceCharacters(String queryString) {
		queryString = queryString.replace(";", "");
		queryString = queryString.replace("=", " = ");
		queryString = queryString.replace(">", " > ");
		queryString = queryString.replace("<", " < ");
		queryString = queryString.replace(">=", " >= ");
		queryString = queryString.replace("<=", " <= ");
		queryString = queryString.replace("!=", " != ");

		return queryString;

	}

	/*
	 * This method is used to extract the baseQuery from the query string.
	 * BaseQuery contains from the beginning of the query till the where clause
	 */
	public String getBaseQuery(String queryString) {

		String[] temp = null;
		temp = queryString.split("where|order\\s+by|group\\s+by");
		String baseQueryString = temp[0];

		return baseQueryString;

	}

	/*
	 * this method will split the query string based on space into an array of
	 * words
	 */
	public String[] getSplitStrings(String queryString) {

		String[] queryParts = queryString.split("\\s+");
		return queryParts;
	}

	/*
	 * extract the name of the file from the query.
	 */

	public String getFileName(String queryString) {

		String fileName = null;
		try {
			String[] fileNameField = (queryString.split("from"))[1].split("(where)|(order)|(group)\\\\s+by");
			if (!fileNameField[0].trim().isEmpty()) {
				fileName = fileNameField[0].trim();
			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		return fileName;
	}

	/*
	 * extract the order by fields from the query string.
	 */

	public List<String> getOrderByFields(String queryString) {

		List<String> orderBy = null;
		if (queryString.contains(" order by ")) {
			orderBy = new ArrayList<String>();
			String[] orderByFields = (queryString.trim().split("\\s+order\\s+by\\s+"))[1].trim().split(",");
			for (int i = 0; i < orderByFields.length; i++) {
				orderBy.add(orderByFields[i]);
			}
		}
		return orderBy;
	}

	/*
	 * extract the group by fields from the query string.
	 */
	public List<String> getGroupByFields(String queryString) {

		List<String> groupBy = null;

		// Check if Group by clause is present
		if (queryString.contains(" group by ")) {
			groupBy = new ArrayList<String>();
			String groupByPart = (queryString.trim().split("\\s+group\\s+by\\s+"))[1].trim();

			// Check if Order by clause is present
			if (groupByPart.contains(" order by ")) {
				groupByPart = (groupByPart.split("\\s+order\\s+by\\s+"))[0].trim();
			}
			String[] groupByFields = groupByPart.trim().split(",");
			for (int i = 0; i < groupByFields.length; i++) {
				groupBy.add(groupByFields[i]);
			}
		}

		/* Set the groupBy to QueryParamter variable */
		queryParameter.setGroupByFields(groupBy);
		return groupBy;
	}

	/*
	 * extract the selected fields from the query string.
	 */
	public String[] getFields(String queryString) {

		String fields1[] = null;
		List<String> fields = new ArrayList<String>();

		fields1 = (getBaseQuery(queryString).split("select\\s+"))[1].split("\\s+from");
		fields1 = fields1[0].trim().split(",");

		for (int i = 0; i < fields1.length; i++) {
			fields.add(fields1[i].trim());
		}
		queryParameter.setFields(fields);
		return fields1;

	}

	/*
	 * This method is used to extract the conditions part from the query string.
	 */
	public String getConditionsPartQuery(String queryString) {

		String conditionPart = null;
		String[] temp = null;
		if (queryString.contains("where")) {
			temp = (queryString.split("where"))[1].trim().split("(order)|(group)\\s+by");
			conditionPart = temp[0].trim();
		}
		return conditionPart;

	}

	/*
	 * extract the conditions from the query string(if exists)
	 */

	public List<Restriction> getConditions(String queryString) {

		String[] conditions = null;
		List<Restriction> restrictList = queryParameter.getRestrictions();
		if (getConditionsPartQuery(queryString) != null) {
			String conditionPartQuery = getConditionsPartQuery(queryString).trim();
			if (conditionPartQuery.toLowerCase().contains(" and ")
					|| conditionPartQuery.toLowerCase().contains(" or ")) {
				conditions = conditionPartQuery.trim().split("( and )|( or )");
			} else {
				conditions = new String[] { conditionPartQuery };
			}
			restrictList = new ArrayList<Restriction>();
			for (int i = 0; i < conditions.length; i++) {
				String[] temp = conditions[i].split("\\s+");
				Restriction restriction = new Restriction();
				restriction.setPropertyName(temp[0].trim());
				restriction.setPropertyValue(temp[2].trim());
				restriction.setCondition(temp[1].trim());
				restrictList.add(restriction);
			}
		}
		return restrictList;
	}

	/*
	 * extract the logical operators(AND/OR) from the query, if at all it is
	 * present.
	 */
	public List<String> getLogicalOperators(String queryString) {

		List<String> logicalOperator = null;
		String conditionsPartQuery = getConditionsPartQuery(queryString);
		if ((conditionsPartQuery != null)
				&& (conditionsPartQuery.contains(" and ") | conditionsPartQuery.contains(" or "))) {
			logicalOperator = new ArrayList<String>();
			String[] splitCondition = getSplitStrings(conditionsPartQuery.trim());
			for (int i = 0; i < splitCondition.length; i++) {
				if (splitCondition[i].equals("and") | splitCondition[i].equals("or")) {
					logicalOperator.add(splitCondition[i]);
				}
			}

		}

		return logicalOperator;

	}

	/*
	 * extract the aggregate functions from the query.
	 */
	public List<AggregateFunction> getAggregateFunctions(String queryString) {

		List<AggregateFunction> aggregateList = queryParameter.getAggregateFunctions();
		AggregateFunction aggregate = new AggregateFunction();
		String[] fieldsString = getFields(queryString.toLowerCase());
		if ((fieldsString.length != 1) && (!(fieldsString[0].equals("*")))) {
			aggregateList = new ArrayList<AggregateFunction>();
			for (int i = 0; i < fieldsString.length; i++) {
				if (fieldsString[i].contains("(")) {
					aggregate.setFunction((fieldsString[i].split("\\("))[0].trim());
					aggregate.setField((fieldsString[i].split("\\("))[1].trim().split("\\)")[0]);
					aggregateList.add(aggregate);
				}

			}

		}
		return aggregateList;
	}
}
