package com.Java.M13_SimpleSpringHTTPService.Model.DTO;

import lombok.Builder;
import lombok.Data;

/*
 * The Lombok library can be used to reduce 
 * the amount of boilerplate Java code that is commonly written for Java classes
 */
@Data // is like having implicit @Getter, @Setter, ...
@Builder // creates a builder for all instance fields in the class 
public class EmployeeDTO {
	
	private long id;
	private String name;
	private String position;
	private int salary;
}
