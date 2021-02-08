package com.Java.M13_SimpleSpringHTTPService.Model.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	public List<Employee> findByNameEmployeeContaining(String name);
	
	public List<Employee> findByEmployeeAndPosition(String name, String position);
}
