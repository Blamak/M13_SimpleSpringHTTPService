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
		/*
		 * This method is an implementation of the functional interface RowMapper.
		 * It is used to map each row of a ResultSet to an object
		 */
		Employee employee = new Employee();
		employee.setId(resultSet.getLong("id"));
		employee.setName(resultSet.getString("name"));
		employee.setPosition(resultSet.getString("position"));
		
		return employee;
	}

	public List<Employee> findAll() {
		String sqlQuery = "select * from employees";

		return jdbcTemplate.query(sqlQuery, this::mapRowToEmployee);
	}

	public Employee findById(long id) {
		String sqlQuery = "select * from employees where id = ?";

		return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToEmployee, id);

	}

	public List<Employee> findByPosition(String position) {
		String sqlQuery = "select * from employees where position = ?";
		return jdbcTemplate.query(sqlQuery, this::mapRowToEmployee, position);
	}

	/*
	 * The defined delete statement is passed to the update() method. 
	 * The method returns an int, which indicates how many records were affected by the operation.
	 * If the return value is greater than 0, one record was deleted.
	 */
	public boolean deleteById(long id) {
		String sqlQuery = "delete from employees where id = ?";
		
		return jdbcTemplate.update(sqlQuery, id) > 0;
	}

	public void save(Employee employee) {
		String sqlQuery = "insert into employees (name, position) " + "values(?, ?)";
		
		jdbcTemplate.update(sqlQuery,
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
