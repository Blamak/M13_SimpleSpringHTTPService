package com.Java.M13_SimpleSpringHTTPService.Model.Repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;

@Repository
public class EmployeeRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public EmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private Employee mapRowToEmployee(ResultSet resultSet, int rowNum) throws SQLException {
		return Employee.builder()
				.id(resultSet.getLong("id"))
				.name(resultSet.getString("name"))
				.position(resultSet.getString("position"))
				.build();
	}

	public List<Employee> findAll() {
		String sqlQuery = "select * from employees";
		return jdbcTemplate.query(sqlQuery, this::mapRowToEmployee);
	}

	public Employee findById(long id) {
		String sqlQuery = "select id, name, position " + "from employees where id = ?";
		return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToEmployee, id);
	}

	public List<Employee> findByPosition(String position) {
		String sqlQuery = "select * from employees where position = ?";
		return jdbcTemplate.query(sqlQuery, this::mapRowToEmployee, position);
	}

	public int deleteById(long id) {
		String sqlQuery = "delete from employees where id = ?";
		return jdbcTemplate.update(sqlQuery, id);
	}

	public int save(Employee employee) {
		String sqlQuery = "insert into employees (name, position) " + "values(?, ?)";
		return jdbcTemplate.update(sqlQuery,
				            employee.getName(),
				            employee.getPosition());
	}

	public void update(Employee employee) {
		String sqlQuery = "update employees " + " set name = ?, position = ? " + " where id = ?";
		jdbcTemplate.update(sqlQuery, 
				            employee.getName(),
				            employee.getPosition(),
				            employee.getId());
	}

}
