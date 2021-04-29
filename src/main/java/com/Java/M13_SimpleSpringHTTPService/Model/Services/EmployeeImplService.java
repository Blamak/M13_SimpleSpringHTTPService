package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
			List<EmployeeDTO> listEmployeesDTO = new ArrayList<EmployeeDTO>(); 
			
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
		// check if the body's request "position" exists in the enum PositionsEnum. Return error Response if not.
		Boolean positionExists = Arrays.stream(PositionsEnum.values())
									   .anyMatch( position -> position.name().equals(employeeDTO.getPosition()) );
		
		if (!positionExists) return new Response(
				"Error", new ParamNotFoundException(employeeDTO.getPosition()).getMessage());
		
		try {
			//convert to entity and save new employee to database
			Employee newEmployee = this.mapDtotoEntity(employeeDTO);
			employeeRepository.save(newEmployee);
			return new Response("OK", employeeDTO);
		} catch (Exception e) {
			return new Response("Error", e.getMessage());
		}
	}

	@Override
	public Response replaceEmployee(EmployeeDTO employeeDTO, long id) {
		// check if the body's request "position" exists in the enum PositionsEnum
		Boolean positionExists = Arrays.stream(PositionsEnum.values())
								       .anyMatch( position -> position.name().equals(employeeDTO.getPosition()) );

		if (!positionExists) return new Response(
				"Error", new ParamNotFoundException(employeeDTO.getPosition()).getMessage());

		try {
			Employee employee = employeeRepository.findById(id);
			// send error message if there is no change in the employee's info
			if ( employee.getName().equals(employeeDTO.getName()) &&
					employee.getPosition().equals(employeeDTO.getPosition()) )
				return new Response("Error", "nothing changed for this employee");
			
			// modify employee's info in database
			employee.setName(employeeDTO.getName());
			employee.setPosition(employeeDTO.getPosition());
			employeeRepository.update(employee);
		
			EmployeeDTO empDTO = this.mapEntitytoDTO(employee);
			return new Response("OK", empDTO);
		} catch (EmptyResultDataAccessException e) { // error response for non-existent id
			return new Response("Error", new ParamNotFoundException(id).getMessage());
		} catch (Exception e) {
			return new Response("Error", e.getMessage());
		}
	}

	@Override
	public Response deleteById(long id) {
		try {			
			Employee removedEmployee = employeeRepository.findById(id);
			employeeRepository.deleteById(id);			
			return new Response("OK", removedEmployee);
		} catch (EmptyResultDataAccessException e) { // error response for non-existent id
			return new Response("Error", new ParamNotFoundException(id).getMessage());
		} catch (Exception e) {
			return new Response("Error", e.getMessage());
		}
	}

	
// --------------------------------- Methods called in requests NOT available in UI -------------------------- //
	
	@Override
	public Response getByPosition(String position) {
		try {
			List<EmployeeDTO> listByPosition = employeeRepository.findByPosition(position).stream()
					                           .map(employee -> this.mapEntitytoDTO(employee))
					                           .collect(Collectors.toList());
			if (listByPosition.isEmpty()) {
				// error response if position doesn't exist (= empty list)
				return new Response("Error", new ParamNotFoundException(position).getMessage());
			} else {
				return new Response("Ok", listByPosition);
			}
		} catch (Exception e) {
			return new Response("Error", e.getMessage());
		}
	}

	@Override
	public Response getById(long id) {
		try {
			EmployeeDTO empDTO = this.mapEntitytoDTO(employeeRepository.findById(id));
			return new Response("OK", empDTO);
		} catch (EmptyResultDataAccessException e) { // error response for non-existent id
			return new Response("Error", new ParamNotFoundException(id).getMessage());
		}  catch (Exception e) {
			return new Response("Error", e.getMessage());
		}
	}

	
// --------------------------------------- Methods for DTO←→Entity conversion -------------------------------- //
	
	private Employee mapDtotoEntity(EmployeeDTO dto) {
		return Employee.builder()
				.id(dto.getId())
				.name(dto.getName())
				.position(dto.getPosition())
				.build();
	}

	private EmployeeDTO mapEntitytoDTO(Employee entity) {
		// first extract entity's position to calculate salary (enum...)
		String positionReturned = entity.getPosition();
		int employeeSalary = PositionsEnum.valueOf(positionReturned).showSalary();

		return EmployeeDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.position(positionReturned)
				.salary(employeeSalary)
				.build();
	}

}
