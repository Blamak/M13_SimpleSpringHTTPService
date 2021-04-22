package com.Java.M13_SimpleSpringHTTPService.Model.DTO;

public class EmployeeDTO {
	
	private Integer id;
	private String name;
	private String position;
	private int salary;
	
	/*
	 * Getter that calls the enum method to get the corresponding salary
	 */
	public int getSalary() {
		salary = PositionEnum.valueOf(this.position).showSalary();
		return salary;
	}


	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
