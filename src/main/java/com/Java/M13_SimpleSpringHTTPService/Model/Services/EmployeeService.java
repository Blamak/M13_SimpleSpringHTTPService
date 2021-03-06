package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.List;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Response.Response;

public interface EmployeeService {
	
	Response getAllEmployees();
	
	Response saveEmployee(EmployeeDTO employeeDTO);
	
	Response replaceEmployee(EmployeeDTO employeeDTO, Integer id);
	
	Response deleteById(Integer id);
	

	EmployeeDTO getEmployeeById(Integer id);
	List<EmployeeDTO> getEmployeeByPosition(String position);

}
