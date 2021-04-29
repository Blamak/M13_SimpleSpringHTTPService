package com.Java.M13_SimpleSpringHTTPService.Model.DTO;

/**
 * enum to define every possible job position and its corresponding remuneration
 * 
 * The salary is not stored in database, only showed through the DTO;
 * showSalary() method is called in the DTO conversion method of the service layer 
 * 
 */ 
public enum PositionsEnum {
	
	CEO(1500), COO(1600), CFO(1700), CIO(1800), CMO(1900), CTO(2000);
	private int salary;

	PositionsEnum(int salary) {
		this.salary = salary;
	}
	
	public int showSalary() {
		return this.salary;
	}
}