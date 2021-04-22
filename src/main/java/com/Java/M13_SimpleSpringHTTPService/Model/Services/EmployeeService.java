package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.List;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;

public interface EmployeeService {
	
	List<EmployeeDTO> getAllEmployees();
	Employee saveEmployee(EmployeeDTO employeeDTO);
	Employee replaceEmployee(EmployeeDTO employeeDTO, Integer id);
	void deleteEmployee(Integer id);
	
	
	List<EmployeeDTO> getEmployeeByPosition(String position);
	EmployeeDTO getEmployeeById(Integer id);

}
