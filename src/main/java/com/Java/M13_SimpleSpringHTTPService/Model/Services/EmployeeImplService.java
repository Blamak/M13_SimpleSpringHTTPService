package com.Java.M13_SimpleSpringHTTPService.Model.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;
import com.Java.M13_SimpleSpringHTTPService.Model.Repositories.EmployeeRepository;

@Service
public class EmployeeImplService implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Integer saveEmployee(EmployeeDTO employeeDTO) {
		Employee emp = this.mapDtotoEntity(employeeDTO);
		emp = employeeRepository.save(emp);
		return emp.getId();
	}

	@Override
	public List<EmployeeDTO> getEmployees() {
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
	public EmployeeDTO getEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EmployeeDTO> getEmployeesByFilter(String name, String position) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
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
