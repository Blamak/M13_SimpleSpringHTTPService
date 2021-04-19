package com.Java.M13_SimpleSpringHTTPService.Model.Repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.Java.M13_SimpleSpringHTTPService.Model.Entities.Employee;

@Repository
public class EmployeeRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	class EmployeeRowMapper implements RowMapper<Employee> {
		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee employee = new Employee();
			employee.setId(rs.getLong("id"));
			employee.setName(rs.getString("name"));
			employee.setPosition(rs.getString("position"));
			return employee;
		}
	}

	public List<Employee> findAll() {
		return jdbcTemplate.query("select * from employees", new EmployeeRowMapper());
	}

	public Optional<Employee> findById(long id) {
		return jdbcTemplate.queryForObject(
                "select * from employees where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Employee(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("position")
                        ))
        );
//
//		String query = "SELECT * FROM EMPLOYEES WHERE ID = ?";
//
//		Employee employee = jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), id);
//
//		return employee;

	}
	

	    public List<Employee> findByPosition(String position) {
	        return jdbcTemplate.query(
	                "select * from employees where position = ?",
	                new Object[]{"%" + position},
	                (rs, rowNum) ->
	                        new Employee(
	                                rs.getLong("id"),
	                                rs.getString("name"),
	                                rs.getString("position")
	                        )
	        );
	    }

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from employees where id=?", new Object[] { id });
	}

	public int save(Employee employee) {
		return jdbcTemplate.update("insert into employees (name, position) " + "values(?, ?)",
				new Object[] { employee.getName(), employee.getPosition() });
	}

	public int update(Employee employee) {
		return jdbcTemplate.update("update employees " + " set name = ?, position = ? " + " where id = ?",
				employee.getName(), employee.getPosition(), employee.getId());
	}

}
