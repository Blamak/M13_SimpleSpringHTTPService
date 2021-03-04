package com.Java.M13_SimpleSpringHTTPService.Exceptions;

/**
 * Clase para crear una respuesta de error personalizada
 * para las peticiones de búsqueda por id y por posición
 * en caso de parámetro inexistente (id o posición)
 *
 */
public class ParamNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParamNotFoundException(Integer id) {
		super("Could not find employee " + id);
	}
	
	public ParamNotFoundException(String position) {
		super("Could not find position " + position);
	}

}
