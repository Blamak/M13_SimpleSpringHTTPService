package com.Java.M13_SimpleSpringHTTPService.Model.Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {

	private long id;
	private String name;
	private String position;
	
//	public Map<String, Object> toMap() {
//		  Map<String, Object> values = new HashMap<>();
//		  values.put("name", name);
//		  values.put("position", position);
//		  return values;
//		}
}
