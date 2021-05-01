package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Java.M13_SimpleSpringHTTPService.Exceptions.ParamNotFoundException;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.PositionsEnum;
import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;
import com.Java.M13_SimpleSpringHTTPService.Model.Repositories.EmployeeRepository;
import com.Java.M13_SimpleSpringHTTPService.Response.Response;

@Service
public class EmployeeImplService implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	
// ---------------------------- Methods called in requests available in UI -------------------------- //
	
	@Override
	public Response getAllEmployees() {
		try {
			List<Employee> listEmployees = employeeRepository.findAll();
			// empty list to append every employee from the database
			List<EmployeeDTO> listEmployeesDTO = new ArrayList<>();  
			
			if (listEmployees != null && listEmployees.size() > 0) {
				for (Employee employee : listEmployees) {
					listEmployeesDTO.add(this.mapEntitytoDTO(employee));
				}
				return new Response("OK", listEmployeesDTO);
			} else {
				return new Response("Error", "No employees in the database yet"); // empty database error
			}
		} catch (Exception e) {
			return new Response("Error", e.getMessage());
		}
	}

	@Override
	public Response saveEmployee(EmployeeDTO employeeDTO) {
		// check if "position" in the body's request exists in PositionsEnum. Return error Response if not.
		Boolean positionExists = Arrays.stream(PositionsEnum.values())
								       .anyMatch( position -> position.name().equals(employeeDTO.getPosition()) );
		if (!positionExists)
			return new Response("Error", new ParamNotFoundException(employeeDTO.getPosition()).getMessage());
			
		try {
			// convert to entity and save new employee to database
			Employee newEmployee = this.mapDtotoEntity(employeeDTO);
			newEmployee = employeeRepository.save(newEmployee);			
			return new Response("OK", newEmployee);
		} catch (Exception e) {
			return new Response("Error", e.getMessage());
		}
	}

	@Override
	public Response replaceEmployee(EmployeeDTO employeeDTO, Integer id) {
		// check if the body's request "position" exists in PositionsEnum. Return error Response if not.
		Boolean positionExists = Arrays.stream(PositionsEnum.values())
								       .anyMatch( position -> position.name().equals(employeeDTO.getPosition()) );
		if (!positionExists)
			return new Response("Error", new ParamNotFoundException(employeeDTO.getPosition()).getMessage());

		try {
			Employee employee = employeeRepository.findById(id)
					                              .orElseThrow(() -> new ParamNotFoundException(id));
			// send error message if there is no change in the employee's info
			if ( employee.getName().equals(employeeDTO.getName()) &&
					employee.getPosition().equals(employeeDTO.getPosition()) )
				return new Response("Error", "nothing changed for this employee");
			
			// modify employee's info in the database
			employee.setName(employeeDTO.getName());
			employee.setPosition(employeeDTO.getPosition());
			employeeRepository.save(employee);
			
			EmployeeDTO empDTO = this.mapEntitytoDTO(employee);
			return new Response("OK", empDTO);
		} catch (Exception e) {
			return new Response("Error", e.getMessage());
		}
	}

	@Override
	public Response deleteById(Integer id) {
		try {
			Optional<Employee> removedEmployee = employeeRepository.findById(id);
			employeeRepository.deleteById(id);
			Response response = new Response("OK", removedEmployee);
			return response;
		} catch (Exception e) {
			Response response = new Response("Error", e.getMessage());
			return response;
		}
	}

	
// --------------------------------- Methods called in requests NOT available in UI -------------------------- //
	
	@Override
	public List<EmployeeDTO> getEmployeeByPosition(String position) {
		return Optional.ofNullable(
				employeeRepository.findByPosition(position).stream()
				    .map(employee -> this.mapEntitytoDTO(employee))
				    .collect(Collectors.toList()) 
				)
				.filter(list -> !list.isEmpty()) // check if param "position" is not present in any employee
				.orElseThrow(() -> new ParamNotFoundException(position));
	}
	
	@Override
	public EmployeeDTO getEmployeeById(Integer id) {
		return employeeRepository.findById(id)
				.map(employee -> this.mapEntitytoDTO(employee))
				.orElseThrow(() -> new ParamNotFoundException(id));
	}
	

// --------------------------------------- Methods for DTO←→Entity conversion -------------------------------- //

	private Employee mapDtotoEntity(EmployeeDTO dto) {
		Employee emp = new Employee();
		if (dto.getId() != null) {
			emp.setId(dto.getId());
		}
		emp.setName(dto.getName());
		emp.setPosition(dto.getPosition());

		return emp;
	}

	private EmployeeDTO mapEntitytoDTO(Employee entity) {
		// first extract entity's position to get salary
		String positionReturned = entity.getPosition();
		int employeeSalary = PositionsEnum.valueOf(positionReturned).showSalary();
		
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPosition(entity.getPosition());
		dto.setSalary(employeeSalary);
		
		return dto;
	}

}
