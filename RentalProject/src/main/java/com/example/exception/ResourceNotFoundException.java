package com.example.exception;


public class ResourceNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	@ResponseStatus(NOT_FOUND)
	public ResourceNotFoundException(String message) {
        super(message);
    }
}
