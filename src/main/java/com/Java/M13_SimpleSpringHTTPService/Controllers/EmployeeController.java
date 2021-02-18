package com.Java.M13_SimpleSpringHTTPService.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;
import com.Java.M13_SimpleSpringHTTPService.Model.Services.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employees")  // CREATE
	public Employee newEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.saveEmployee(employeeDTO);
	}
	
	@GetMapping("/employees")  // READ
	public List<EmployeeDTO> listEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@PutMapping("/employees/{id}") // UPDATE
	public Employee updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable Integer id) {
		return employeeService.replaceEmployee(employeeDTO, id);
	}
	
	@DeleteMapping("/employees/{id}") // DELETE
	public void deleteEmployee(@PathVariable Integer id) {
		employeeService.deleteById(id);
	}
	
	
	@GetMapping("/employees/position/{position}") // FIND BY POSITION
	public List<EmployeeDTO> listByPosition(@PathVariable String position) {
		return employeeService.getByPosition(position);
	}

	@GetMapping("/employees/id/{id}") // FIND BY ID
	public EmployeeDTO listById(@PathVariable Integer id) {
		return employeeService.getEmployeeById(id);
	}
	
}
