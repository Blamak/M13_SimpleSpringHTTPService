package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.List;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.message.Response;

public interface EmployeeService {
	
	Response getAllEmployees();
	
	Response saveEmployee(EmployeeDTO employeeDTO);
	
	Response replaceEmployee(EmployeeDTO employeeDTO, Integer id);
	
	Response deleteById(Integer id);

	EmployeeDTO getById(Integer id);
	List<EmployeeDTO> getByPosition(String position);

}
