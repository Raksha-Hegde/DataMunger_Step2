package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * this method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {

		if (!queryString.isEmpty()) {
			getFile(replaceCharacters(queryString));
			getFields(replaceCharacters(queryString));
			getConditions(replaceCharacters(queryString));

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
	 * extract the name of the file from the query.
	 */

	public void getFile(String queryString) {

		/* File name can be found after the "from" clause */
		String[] fileName = (queryString.split("from\\s+"))[1].split("where");
		queryParameter.setFile(fileName[0]); // Call setFile method and pass the
												// extracted fileName
	}

	/*
	 * extract the order by fields from the query string. Please note that we
	 * will need to extract the field(s) after "order by" clause in the query,
	 * if at all the order by clause exists. For eg: select
	 * city,winner,team1,team2 from data/ipl.csv order by city from the query
	 * mentioned above, we need to extract "city". Please note that we can have
	 * more than one order by fields.
	 */

	/*
	 * extract the group by fields from the query string. Please note that we
	 * will need to extract the field(s) after "group by" clause in the query,
	 * if at all the group by clause exists. For eg: select
	 * city,max(win_by_runs) from data/ipl.csv group by city from the query
	 * mentioned above, we need to extract "city". Please note that we can have
	 * more than one group by fields.
	 */

	/*
	 * extract the selected fields from the query string. Please note that we
	 * will need to extract the field(s) after "select" clause followed by a
	 * space from the query string. For eg: select city,win_by_runs from
	 * data/ipl.csv from the query mentioned above, we need to extract "city"
	 * and "win_by_runs". Please note that we might have a field containing name
	 * "from_date" or "from_hrs". Hence, consider this while parsing.
	 */
	public void getFields(String queryString) {

		String fields1[] = null;
		List<String> fields = new ArrayList<>();

		fields1 = (queryString.split("select\\s+"))[1].split("\\s+from");
		fields1 = fields1[0].trim().split(",");

		for (int i = 0; i < fields1.length; i++) {
			fields.add(fields1[i].trim());
		}
		queryParameter.setFields(fields);

	}

	/*
	 * extract the conditions from the query string(if exists). for each
	 * condition, we need to capture the following: 1. Name of field 2.
	 * condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name
	 * of field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND
	 * operators. Please consider this while parsing the conditions.
	 * 
	 */

	public void getConditions(String queryString) {

		String[] conditions;
		if (queryString.contains("where")) {
			conditions = (queryString.split("where"))[1].split("(order)|(group)\\s+by");
		}
		queryParameter.set
		
	}

	/*
	 * extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * the query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */

	/*
	 * extract the aggregate functions from the query. The presence of the
	 * aggregate functions can determined if we have either "min" or "max" or
	 * "sum" or "count" or "avg" followed by opening braces"(" after "select"
	 * clause in the query string. in case it is present, then we will have to
	 * extract the same. For each aggregate functions, we need to know the
	 * following: 1. type of aggregate function(min/max/count/sum/avg) 2. field
	 * on which the aggregate function is being applied
	 * 
	 * Please note that more than one aggregate function can be present in a
	 * query
	 * 
	 * 
	 */

}
