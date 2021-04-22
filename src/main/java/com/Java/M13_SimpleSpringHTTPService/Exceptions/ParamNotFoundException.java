package com.Java.M13_SimpleSpringHTTPService.Exceptions;

/**
 * Customized message for ParamNotFoundException
 * 
 * Also thrown when a non-existent job position is introduced in the body parameter  
 * of the POST and PUT requests
 * 
 */
public class ParamNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// wrong id case
	public ParamNotFoundException(Integer id) {
		super("Could not find employee " + id);
	}

	// wrong position case
	public ParamNotFoundException(String position) {
		super("The job position \'" + position + "\' does not exist in our company");
	}

}
