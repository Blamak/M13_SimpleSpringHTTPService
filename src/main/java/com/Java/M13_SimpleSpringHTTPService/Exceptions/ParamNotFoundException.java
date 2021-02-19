package com.Java.M13_SimpleSpringHTTPService.Exceptions;

public class ParamNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParamNotFoundException(Integer id) {
		super("Could not find employee " + id);
	}
	
	public ParamNotFoundException(String position) {
		super("Could not find position " + position);
	}

}
