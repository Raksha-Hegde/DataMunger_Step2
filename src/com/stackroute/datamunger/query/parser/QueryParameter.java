package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {

	private String fileName;
	List<Restriction> restriction;
	private List<String> logicalOperators;
	private List<String> fields;
	private List<AggregateFunction> aggregateFunction;
	private List<String> groupByFields;
	private List<String> orderByFields;

	public String getFile() {
		return fileName;
	}

	public void setFile(String fileName) {
		this.fileName = fileName;
	}

	public List<Restriction> getRestrictions() {
		return restriction;
	}

	public void setRestrictions(List<Restriction> restrictionsList) {
		this.restriction = restrictionsList;

	}

	public List<String> getLogicalOperators() {

		return this.logicalOperators;
	}

	public void setLogicalOperators(List<String> logicalOperator) {
		this.logicalOperators = logicalOperator;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<AggregateFunction> getAggregateFunctions() {

		return aggregateFunction;
	}

	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctionList) {

		this.aggregateFunction = aggregateFunctionList;

	}

	public List<String> getGroupByFields() {
		return groupByFields;
	}

	public void setGroupByFields(List<String> groupBy) {
		this.groupByFields = groupBy;
	}

	public List<String> getOrderByFields() {
		return orderByFields;
	}

	public void setOrderByFields(List<String> orderBy) {
		this.orderByFields = orderBy;
	}
}
