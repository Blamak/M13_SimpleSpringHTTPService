package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Java.M13_SimpleSpringHTTPService.Exceptions.ParamNotFoundException;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
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
		Employee newEmployee = this.mapDtotoEntity(employeeDTO);
		employeeRepository.save(newEmployee);
		
		Response response = new Response("OK", employeeDTO);
		return response;
	}


	/**
	 * Method for updating employee info - PUT request
	 * Creates a new employee if the id sent is new
	 */
	@Override
	public Response replaceEmployee(EmployeeDTO employeeDTO, long id) {
		
		return employeeRepository.findById(id).map(employee -> {
			employee.setName(employeeDTO.getName());
			employee.setPosition(employeeDTO.getPosition());
			employeeRepository.update(employee);
			
			Response response = new Response("OK", employee);
			return response;
			
		}).orElseGet(() -> {
			Employee newEmployee = this.mapDtotoEntity(employeeDTO);
			employeeRepository.save(newEmployee);
			
			Response response = new Response("OK", newEmployee);
			return response;
		});

	}

	@Override
	public Response deleteById(long id) {
		Optional<Employee> removedEmployee = employeeRepository.findById(id);
		employeeRepository.deleteById(id);
		Response response = new Response("OK", removedEmployee);
		return response;
	}

	@Override
	public Response getByPosition(String position) {

		List<EmployeeDTO> listEmp = Optional.ofNullable(employeeRepository.findByPosition(position).stream()
				.map(employee -> this.mapEntitytoDTO(employee)).filter(Objects::nonNull).collect(Collectors.toList()))
				.filter(list -> !list.isEmpty()).orElseThrow(() -> new ParamNotFoundException(position));
		
		Response response = new Response("Ok", listEmp);
		
		return response;
	}

	@Override
	public EmployeeDTO getById(long id) {
		return employeeRepository.findById(id)
				.map(employee -> this.mapEntitytoDTO(employee))
				.orElseThrow(() -> new ParamNotFoundException(id));
	}

	// DTO-entity conversion
	private Employee mapDtotoEntity(EmployeeDTO dto) {
		Employee emp = new Employee();
		if (dto.getId() != 0L) {
			emp.setId(dto.getId());
		}
		emp.setName(dto.getName());
		emp.setPosition(dto.getPosition());

		return emp;
	}

	// Entity-DTO conversion
	private EmployeeDTO mapEntitytoDTO(Employee entity) {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPosition(entity.getPosition());
		return dto;
	}

}
