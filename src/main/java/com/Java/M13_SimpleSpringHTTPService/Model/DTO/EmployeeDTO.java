package com.Java.M13_SimpleSpringHTTPService.Model.DTO;

public class EmployeeDTO {
	
	private Integer id;
	private String name;
	private String position;
	private int salary;
	
	public int getSalary() {
		this.salary = PositionEnum.valueOf(this.position).showSalary();
		return this.salary;
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
