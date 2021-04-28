package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Java.M13_SimpleSpringHTTPService.Exceptions.ParamNotFoundException;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Model.DTO.PositionEnum;
import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;
import com.Java.M13_SimpleSpringHTTPService.Model.Repositories.EmployeeRepository;

@Service
public class EmployeeImplService implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		List<Employee> listEmployees = employeeRepository.findAll();
		// empty list to append every employee from the database
		List<EmployeeDTO> listEmployeesDTO = new ArrayList<EmployeeDTO>();

		if (listEmployees != null && listEmployees.size() > 0) {
			for (Employee employee : listEmployees) {
				listEmployeesDTO.add(this.mapEntitytoDTO(employee));
			}
		}

		return listEmployeesDTO;
	}

	@Override
	public Employee saveEmployee(EmployeeDTO employeeDTO) {
		// check if the body's request "position" exists in the enum
		Boolean positionExists = (Arrays.stream(PositionEnum.values())
				.anyMatch((p) -> p.name().equals(employeeDTO.getPosition())));

		if (!positionExists) {
			throw new ParamNotFoundException(employeeDTO.getPosition());
		} else {
			Employee newEmployee = this.mapDtotoEntity(employeeDTO);
			newEmployee = employeeRepository.save(newEmployee);

			return newEmployee;
		}
	}

	@Override
	public Employee replaceEmployee(EmployeeDTO employeeDTO, Integer id) {
		// check if the body's request "position" exists in the enum; Error response if not
		Boolean positionExists = (Arrays.stream(PositionEnum.values())
				.anyMatch((p) -> p.name().equals(employeeDTO.getPosition())));
		if (!positionExists) throw new ParamNotFoundException(employeeDTO.getPosition());
		
		// modify employee's info in the database
		return employeeRepository.findById(id).map(employee -> {
			employee.setName(employeeDTO.getName());
			employee.setPosition(employeeDTO.getPosition());
			return employeeRepository.save(employee);
		}).orElseThrow(() -> new ParamNotFoundException(id)); // non-existent id 
	}

	@Override
	public void deleteEmployee(Integer id) {
		if (employeeRepository.findById(id).isEmpty()) {
			throw new ParamNotFoundException(id);
		} else {
			employeeRepository.deleteById(id);
		}
	}

	@Override
	public List<EmployeeDTO> getEmployeeByPosition(String position) {
		return Optional.ofNullable(employeeRepository.findByPosition(position).stream()
				.map(employee -> this.mapEntitytoDTO(employee))
				    .filter(Objects::nonNull)
				    .collect(Collectors.toList()))
				.filter(list -> !list.isEmpty()) // check if param "position" is not present in any employee
				.orElseThrow(() -> new ParamNotFoundException(position));
	}

	@Override
	public EmployeeDTO getEmployeeById(Integer id) {
		return employeeRepository.findById(id)
				.map(employee -> this.mapEntitytoDTO(employee))
				.orElseThrow(() -> new ParamNotFoundException(id));
	}
	

// ---------------------------- DTO←→Entity conversions: -------------------------------- //

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
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPosition(entity.getPosition());

		return dto;
	}

}
