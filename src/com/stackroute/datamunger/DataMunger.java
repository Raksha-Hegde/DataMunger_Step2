package com.stackroute.datamunger;

import java.util.Scanner;
import com.stackroute.datamunger.query.parser.*;

public class DataMunger {

	public static void main(String[] args) {

		
		//read the query from the user
		System.out.println("Enter a query: ");
		Scanner scannerObj = new Scanner(System.in);
		String queryString = scannerObj.nextLine();
		scannerObj.close();
		
		
		//create an object of QueryParser class
		QueryParser queryParser = new QueryParser();
		
		//create an object of QueryParameter class
		QueryParameter queryParameter = new QueryParameter();
		
		
		/*
		 * call parseQuery() method of the class by passing the query string which will
		 * return object of QueryParameter
		 */
		
		queryParameter = queryParser.parseQuery(queryString);
		

	}

	

}
