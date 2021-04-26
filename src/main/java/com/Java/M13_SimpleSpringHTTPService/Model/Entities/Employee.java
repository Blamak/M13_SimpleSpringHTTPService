package com.Java.M13_SimpleSpringHTTPService.Model.Entities;

/*
 * The Lombok library can be used to reduce 
 * the amount of boilerplate Java code that is commonly written for Java classes
 */
import lombok.Builder;
import lombok.Data;

@Data // is like having implicit @Getter, @Setter, ...
@Builder // creates a builder for all instance fields in the class 
public class Employee {

	private long id;
	private String name;
	private String position;
	
}
