package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.List;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;

public interface EmployeeService {
	
	List<EmployeeDTO> getAllEmployees();
	
	EmployeeDTO getEmployeeById(Integer id);
	
	Employee saveEmployee(EmployeeDTO employeeDTO);
	
	Employee replaceEmployee(EmployeeDTO employeeDTO, Integer id);
	
	void deleteById(Integer id);
	
	List<EmployeeDTO> getByPosition(String position);

}
