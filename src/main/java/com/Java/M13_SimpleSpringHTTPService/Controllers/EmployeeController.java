package com.Java.M13_SimpleSpringHTTPService.Controllers;

import java.util.List;

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

	// ----------- Requests through Ajax: --------- //
	
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
	
	
	// ------------ Requests only from url: ---------- //
	
	// FIND BY POSITION
	@GetMapping("/position/{position}")
	public List<EmployeeDTO> listByPosition(@PathVariable String position) {
		return employeeService.getByPosition(position);
	}
	
	// FIND BY ID
	@GetMapping("/id/{id}")
	public Response listById(@PathVariable long id) {
		return employeeService.getById(id);
	}
}
