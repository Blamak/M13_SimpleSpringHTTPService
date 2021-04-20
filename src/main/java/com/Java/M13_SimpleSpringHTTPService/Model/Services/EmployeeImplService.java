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

@Service
public class EmployeeImplService implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		List<EmployeeDTO> listEmployeesDTO = null;
		List<Employee> listEmployees = employeeRepository.findAll();
		
		if (listEmployees != null && listEmployees.size() > 0) {
			listEmployeesDTO = new ArrayList<EmployeeDTO>();
			for (Employee employee : listEmployees) {
				listEmployeesDTO.add(this.mapEntitytoDTO(employee));
			}
		}
		
		return listEmployeesDTO;
	}

	@Override
	public Employee saveEmployee(EmployeeDTO employeeDTO) {
		Employee newEmployee = this.mapDtotoEntity(employeeDTO);
		newEmployee = employeeRepository.save(newEmployee);

		return newEmployee;
	}

	@Override
	public Employee replaceEmployee(EmployeeDTO employeeDTO, Integer id) {
		return employeeRepository.findById(id).map(employee -> {
			employee.setName(employeeDTO.getName());
			employee.setPosition(employeeDTO.getPosition());
			return employeeRepository.save(employee);
		}).orElseThrow(() -> new ParamNotFoundException(id));

	}

	@Override
	public void deleteEmployeeById(Integer id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public List<EmployeeDTO> getEmployeeByPosition(String position) {
		return Optional.ofNullable(employeeRepository.findByPosition(position).stream()
				.map(employee -> this.mapEntitytoDTO(employee))
					.filter(Objects::nonNull).collect(Collectors.toList()))
				.filter(list -> !list.isEmpty())
				.orElseThrow(() -> new ParamNotFoundException(position));
	}

	@Override
	public EmployeeDTO getEmployeeById(Integer id) {
		return employeeRepository.findById(id)
				.map(employee -> this.mapEntitytoDTO(employee))
				.orElseThrow(() -> new ParamNotFoundException(id));
	}
	
	
	// ------------ DTO←→Entity conversions: ---------- //
	
	private Employee mapDtotoEntity(EmployeeDTO dto) { // DTO-entity conversion
		Employee emp = new Employee();
		if (dto.getId() != null) {
			emp.setId(dto.getId());
		}
		emp.setName(dto.getName());
		emp.setPosition(dto.getPosition());

		return emp;
	}

	// Entity-DTO conversion
	private EmployeeDTO mapEntitytoDTO(Employee entity) { // Entity-DTO conversion
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPosition(entity.getPosition());
		
		return dto;
	}

}
