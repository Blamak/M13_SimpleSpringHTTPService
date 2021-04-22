package com.Java.M13_SimpleSpringHTTPService.Model.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDTO {
	
	private long id;
	private String name;
	private String position;
	private int salary;
}
