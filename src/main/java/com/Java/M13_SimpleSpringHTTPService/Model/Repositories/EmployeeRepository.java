package com.Java.M13_SimpleSpringHTTPService.Model.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	public List<Employee> findByPosition(String position);
	
}
