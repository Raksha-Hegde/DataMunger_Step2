package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for 
 * each aggregate function
 * */
public class AggregateFunction {
	
	private String field;
	private String function;
	
	public String getField() {
		return field;
	}
	
	public String getFunction() {
		return function;
	}
}
