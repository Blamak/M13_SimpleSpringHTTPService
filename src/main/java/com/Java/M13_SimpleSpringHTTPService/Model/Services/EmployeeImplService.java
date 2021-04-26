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

	@Override
	public Response getAllEmployees() {
		List<EmployeeDTO> listEmployeesDTO = null;
		List<Employee> listEmployees = employeeRepository.findAll();

		if (listEmployees != null && listEmployees.size() > 0) {
			listEmployeesDTO = new ArrayList<EmployeeDTO>();
			for (Employee employee : listEmployees) {
				listEmployeesDTO.add(this.mapEntitytoDTO(employee));
			}
		}

		Response response = new Response("OK", listEmployeesDTO);
		return response;
	}

	@Override
	public Response saveEmployee(EmployeeDTO employeeDTO) {
		// check if the body's request "position" exists in the enum PositionsEnum
		Boolean positionExists = Arrays.stream(PositionsEnum.values())
									   .anyMatch( position -> position.name().equals(employeeDTO.getPosition()) );
		
		if (!positionExists) {
			Response response = new Response("Error", new ParamNotFoundException(employeeDTO.getPosition()).getMessage());
			return response;
		}

		Employee newEmployee = this.mapDtotoEntity(employeeDTO);
		employeeRepository.save(newEmployee);

		Response response = new Response("OK", employeeDTO);
		return response;
	}

	@Override
	public Response replaceEmployee(EmployeeDTO employeeDTO, long id) {
		// check if the body's request "position" exists in the enum PositionsEnum
		Boolean positionExists = Arrays.stream(PositionsEnum.values())
								       .anyMatch( position -> position.name().equals(employeeDTO.getPosition()) );
		if (!positionExists) {
			Response response = new Response("Error", new ParamNotFoundException(employeeDTO.getPosition()).getMessage());
			return response;
		}

		// modify employee's data - error response for non-existent id
		try {
			Employee employee = employeeRepository.findById(id);
			employee.setName(employeeDTO.getName());
			employee.setPosition(employeeDTO.getPosition());
			employeeRepository.update(employee);
			
			EmployeeDTO empDTO = this.mapEntitytoDTO(employee);
			Response response = new Response("OK", empDTO);
			return response;
		} catch (EmptyResultDataAccessException e) {
			Response response = new Response("Error", new ParamNotFoundException(id).getMessage());
			return response;
		}
	}

	@Override
	public Response deleteById(long id) {
		try {			
			Employee removedEmployee = employeeRepository.findById(id);
			employeeRepository.deleteById(id);			
			Response response = new Response("OK", removedEmployee);
			return response;
		} catch (EmptyResultDataAccessException e) {
			Response response = new Response("Error", new ParamNotFoundException(id).getMessage());
			return response;
		}

	}

	@Override
	public Response getByPosition(String position) {
		List<EmployeeDTO> listByPosition = employeeRepository.findByPosition(position).stream()
				.map(employee -> this.mapEntitytoDTO(employee))
				.collect(Collectors.toList());
		
		if (listByPosition.isEmpty()) {
			// error response if position doesn't exist (= empty list)
			Response response = new Response("Error", new ParamNotFoundException(position).getMessage());
			return response;
		} else {		
			Response response = new Response("Ok", listByPosition);
			return response;
		}
	}

	@Override
	public Response getById(long id) {
		try {
			EmployeeDTO empDTO = this.mapEntitytoDTO(employeeRepository.findById(id));
			Response response = new Response("OK", empDTO);
			return response;
		} catch (EmptyResultDataAccessException e) {
			Response response = new Response("Error", new ParamNotFoundException(id).getMessage());
			return response;
		}

	}

	// DTO-entity conversion
	private Employee mapDtotoEntity(EmployeeDTO dto) {
		return Employee.builder()
				.id(dto.getId())
				.name(dto.getName())
				.position(dto.getPosition())
				.build();
	}

	// Entity-DTO conversion
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
