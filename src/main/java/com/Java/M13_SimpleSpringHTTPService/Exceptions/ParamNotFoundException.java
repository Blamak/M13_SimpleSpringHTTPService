package com.Java.M13_SimpleSpringHTTPService.Exceptions;

/**
 * Customized error response in case of wrong id or position
 * in the requests
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
		super(position + " is not a valid job position");
	}

}
