package com.Java.M13_SimpleSpringHTTPService.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Model.Services.EmployeeService;
import com.Java.M13_SimpleSpringHTTPService.Response.Response;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;


// ------------------------------------ Requests available in UI ----------------------------- //
	
	@PostMapping("") // CREATE
	public Response newEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.saveEmployee(employeeDTO);
	}
	
	@GetMapping("") // READ
	public Response listEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@PutMapping("/{id}") // UPDATE
	public Response updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable long id) {
		return employeeService.replaceEmployee(employeeDTO, id);
	}
	
	@DeleteMapping("/{id}") // DELETE
	public Response deleteEmployee(@PathVariable long id) {
		return employeeService.deleteById(id);
	}
	

	// ------------------------------------ Requests NOT available in UI ----------------------------- //
	
	@GetMapping("/position/{position}") // FIND BY POSITION
	public Response listByPosition(@PathVariable String position) {
		return employeeService.getByPosition(position);
	}
	
	@GetMapping("/id/{id}") // FIND BY ID
	public Response listById(@PathVariable long id) {
		return employeeService.getById(id);
	}
}
