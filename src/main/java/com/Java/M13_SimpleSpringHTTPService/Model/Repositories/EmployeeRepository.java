package com.Java.M13_SimpleSpringHTTPService.Model.Repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;


/*
 * Use of RowMapper interface to fetch the records from the database using methods of JdbcTemplate class
 *  
 * In the execute of the '.query' methods the instance of RowMapper must be passed
 * 
 */
@Repository
public class EmployeeRepository implements RowMapper<Employee>{

	// to connect to the database and execute SQL queries
	private final JdbcTemplate jdbcTemplate;

	public EmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * RowMapper interface is used by JdbcTemplate for mapping rows of a ResultSet
	 * on a per-row basis to map a row of a result set to the Employee record 
	 */
	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Employee.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.position(rs.getString("position"))
				.build();
	}
	
	public List<Employee> findAll() {
		String sqlQuery = "select * from employees";
		return jdbcTemplate.query(sqlQuery, this::mapRow);
	}

	public Employee findById(long id) {
		String sqlQuery = "select id, name, position " + "from employees where id = ?";
		return jdbcTemplate.queryForObject(sqlQuery, this::mapRow, id);
	}

	public List<Employee> findByPosition(String position) {
		String sqlQuery = "select * from employees where position = ?";
		return jdbcTemplate.query(sqlQuery, this::mapRow, position);
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
