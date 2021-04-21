package com.Java.M13_SimpleSpringHTTPService.Model.DTO;

public enum PositionEnum {
	
	CEO(1500), COO(1600), CFO(1700), CIO(1800), CMO(1900), CTO(2000);
	int salary;

	PositionEnum(int salary) {
		this.salary = salary;
	}
	
	int showSalary() {
		return salary;
	}
	
}
