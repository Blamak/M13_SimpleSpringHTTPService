package com.Java.M13_SimpleSpringHTTPService.Exceptions;

/**
 * Customized message for ParamNotFoundException
 *
 */
public class ParamNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParamNotFoundException(Integer id) {
		super("Could not find employee " + id);
	}

	
	public ParamNotFoundException(String position) {
		super("The job position \'" + position + "\' does not exist in our company");
	}

}
