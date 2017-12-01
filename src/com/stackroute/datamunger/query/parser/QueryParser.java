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
			getFileName(replaceCharacters(queryString));
			getConditions(replaceCharacters(queryString));
			getLogicalOperators(replaceCharacters(queryString));
			getFields(replaceCharacters(queryString));
			getOrderByFields(replaceCharacters(queryString));
			getGroupByFields(replaceCharacters(queryString));

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
	 * this method will split the query string based on space into an array of words
	 */
	public String[] getSplitStrings(String queryString) {

		queryString = queryString.toLowerCase();
		String[] queryParts = queryString.split("\\s+");
		return queryParts;
	}

	/*
	 * extract the name of the file from the query.
	 */

	public void getFileName(String queryString) {
		String[] fileName = (queryString.split("from\\s+"))[1].trim().split("(where)|(order)|(group)\\\\s+by");
		queryParameter.setFile(fileName[0].trim());
	}

	/*
	 * extract the order by fields from the query string.
	 */

	public void getOrderByFields(String queryString) {

		List<String> orderBy = new ArrayList<String>();
		if (queryString.contains(" order by ")) {

			String[] orderByFields = (queryString.trim().split("\\s+order\\s+by\\s+"))[1].trim().split(",");
			for (int i = 0; i < orderByFields.length; i++)
				orderBy.add(orderByFields[i]);
		}
		queryParameter.setOrderByFields(orderBy);
	}

	/*
	 * extract the group by fields from the query string.
	 */
	public void getGroupByFields(String queryString) {

		List<String> groupBy = new ArrayList<String>();
		if (queryString.contains(" group by ")) {

			String[] groupByFields = (queryString.trim().split("\\s+group\\s+by\\s+"))[1].trim().split(",");
			for (int i = 0; i < groupByFields.length; i++)
				groupBy.add(groupByFields[i]);
		}
		queryParameter.setGroupByFields(groupBy);
	}

	/*
	 * extract the selected fields from the query string.
	 */
	public String[] getFields(String queryString) {

		String fields1[] = null;
		List<String> fields = new ArrayList<String>();

		fields1 = (queryString.split("select\\s+"))[1].split("\\s+from");
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
			temp = (queryString.toLowerCase().split("where"))[1].trim().split("(order)|(group)\\s+by");
			conditionPart = temp[0].trim();
		}
		return conditionPart;

	}

	/*
	 * extract the conditions from the query string(if exists)
	 */

	public void getConditions(String queryString) {

		String[] conditions = null;
		if (getConditionsPartQuery(queryString) != null) {
			String conditionPartQuery = getConditionsPartQuery(queryString).trim();
			if (conditionPartQuery.toLowerCase().contains(" and ")
					|| conditionPartQuery.toLowerCase().contains(" or ")) {
				conditions = conditionPartQuery.trim().split("( and )|( or )");
			} else {
				conditions = new String[] { conditionPartQuery };
			}
			getConditionsSplitPart(conditions);

		}
	}

	/*
	 * extract 1. Name of field 2. condition 3. value for each condition
	 */

	public void getConditionsSplitPart(String[] conditions) {

		for (int i = 0; i < conditions.length; i++) {
			String[] temp = getSplitStrings(conditions[i]);
			Restriction r = new Restriction();
			r.setPropertyName(temp[0].trim());
			r.setCondition(temp[1].trim());
			r.setPropertyValue(temp[2].trim());

		}

	}

	/*
	 * extract the logical operators(AND/OR) from the query, if at all it is
	 * present.
	 */
	public void getLogicalOperators(String queryString) {

		List<String> logicalOperator = null;
		String conditionsPartQuery = getConditionsPartQuery(queryString);
		if (conditionsPartQuery != null) {
			if (conditionsPartQuery.contains(" and ") | conditionsPartQuery.contains(" or ")) {
				logicalOperator = new ArrayList<String>();
				String[] splitCondition = getSplitStrings(conditionsPartQuery.trim());
				for (int i = 0; i < splitCondition.length; i++)
					if (splitCondition[i].equals("and") | splitCondition[i].equals("or")) {
						logicalOperator.add(splitCondition[i]);
					}

			}

		}
		queryParameter.setLogicalOperators(logicalOperator);

	}

	/*
	 * extract the aggregate functions from the query.
	 */
	public void getAggregateFunctions(String queryString) {

		AggregateFunction e = new AggregateFunction();
		String[] fieldsString = getFields(queryString.toLowerCase());
		if ((fieldsString.length == 1) && (fieldsString[0].equals("*"))) {

			// queryParameter.setAggregateFunctions(null);

		} else {
			for (int i = 0; i < fieldsString.length; i++) {

				if (fieldsString[i].contains("(")) {
					e.setFunction((fieldsString[i].split("("))[0].trim());
					e.setField((fieldsString[i].split("("))[1].trim());
					
					 queryParameter.setAggregateFunctions(e);
				}

			}

		}

	}
}
