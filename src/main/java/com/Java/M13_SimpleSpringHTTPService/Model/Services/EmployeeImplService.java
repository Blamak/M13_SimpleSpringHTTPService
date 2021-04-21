package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	@Override
	public Response replaceEmployee(EmployeeDTO employeeDTO, long id) {

		Employee emp = employeeRepository.findById(id);
		emp.setName(employeeDTO.getName());
		emp.setPosition(employeeDTO.getPosition());
		employeeRepository.update(emp);

		EmployeeDTO empDTO = this.mapEntitytoDTO(emp);

		Response response = new Response("OK", empDTO);
		return response;

	}

	@Override
	public Response deleteById(long id) {
		Employee removedEmployee = employeeRepository.findById(id);
		employeeRepository.deleteById(id);

		Response response = new Response("OK", removedEmployee);
		return response;
	}

	@Override
	public List<EmployeeDTO> getByPosition(String position) {

		return Optional.ofNullable(employeeRepository.findByPosition(position).stream()
				.map(employee -> this.mapEntitytoDTO(employee)).filter(Objects::nonNull).collect(Collectors.toList()))
				.filter(list -> !list.isEmpty()).orElseThrow(() -> new ParamNotFoundException(position));
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
		return EmployeeDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.position(entity.getPosition())
				.build();
	}

}
