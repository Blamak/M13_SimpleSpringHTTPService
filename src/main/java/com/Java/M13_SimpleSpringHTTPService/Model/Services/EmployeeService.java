package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.List;

import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;

public interface EmployeeService {
	
	Integer saveEmployee(EmployeeDTO employeeDTO);
	
	List<EmployeeDTO> getEmployees();
	
	EmployeeDTO getEmployeeById(Integer id);
	
	void deleteById(Integer id);
	
	List<EmployeeDTO> getEmployeesByFilter(String name, String position);

}
