package com.Java.M13_SimpleSpringHTTPService.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Java.M13_SimpleSpringHTTPService.Model.DTO.EmployeeDTO;
import com.Java.M13_SimpleSpringHTTPService.Model.Filters.EmployeeFilter;
import com.Java.M13_SimpleSpringHTTPService.Model.Services.EmployeeService;

public class EmployeeController {
	
	private static final String ID_FORM_DTO = "EmployeeDTOForm";
	private static final String ID_LIST_DTO = "EmployeeDTOList";
	private static final String ID_DETAIL_DTO = "EmployeeDTODetail";
	private static final String ID_FILTER_DTO = "EmployeeDTOFilter";

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/ListEmployees")
	public ModelAndView listEmployees() {
		ModelAndView modelAndView = new ModelAndView();
		List<EmployeeDTO> employees = employeeService.getEmployees();
		modelAndView.addObject(ID_LIST_DTO, employees);
		modelAndView.addObject(ID_FILTER_DTO, new EmployeeFilter());
		modelAndView.setViewName("ListEmployees");
		
		return modelAndView;
	}
	
	@GetMapping("/NewEmployee")
	public ModelAndView pageNewEmployee() {
		ModelAndView modelAndView = new ModelAndView();
		EmployeeDTO employeeDTO = new EmployeeDTO();
		modelAndView.addObject(ID_FORM_DTO, employeeDTO);
		modelAndView.setViewName("EditEmployee");
		
		return modelAndView;
	}
	
	@GetMapping("/EditEmployee/{id}")
	public ModelAndView editarEmployee(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView();
		EmployeeDTO dto = employeeService.getEmployeeById(id);
		modelAndView.addObject(ID_FORM_DTO, dto);
		modelAndView.setViewName("EditEmployee");
		
		return modelAndView;
	}
	
	public ModelAndView deleteEmployee(@PathVariable("id") Integer id) {
		employeeService.deleteById(id);
		return new ModelAndView("redirect:/ListEmployees");
	}

	@PostMapping("/SaveEmployee")
	public ModelAndView saveEmployee(@ModelAttribute(ID_FORM_DTO) EmployeeDTO employeeDTO) {
		employeeService.saveEmployee(employeeDTO);
		return new ModelAndView("redirect:/ListEmployees");
	}
}






















