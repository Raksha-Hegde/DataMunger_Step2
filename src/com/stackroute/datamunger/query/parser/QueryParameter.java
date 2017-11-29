package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {
	
	private String fileName;
	private List<Restriction> restriction = new ArrayList<Restriction>();
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
	
	public void setRestrictions(String s1, String s2, String s3) {
		((Restriction) restriction).setPropertyName(s1);
	}
	
	public List<String> getLogicalOperators() {
		return logicalOperators;
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
	
	public List<String> getGroupByFields() {
		return groupByFields;
	}
	
	public List<String> getOrderByFields() {
		return orderByFields;
	}
}
