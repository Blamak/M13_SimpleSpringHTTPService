package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Response.Response;

public interface EmployeeService {
	
	Response getAllEmployees();
	
	Response saveEmployee(EmployeeDTO employeeDTO);
	
	Response replaceEmployee(EmployeeDTO employeeDTO, long id);
	
	Response deleteById(long id);

	EmployeeDTO getById(long id);
	Response getByPosition(String position);

}
