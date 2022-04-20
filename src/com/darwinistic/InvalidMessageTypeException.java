package com.darwinistic;

public class InvalidMessageTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 176911609184519627L;
	
	public InvalidMessageTypeException(String message) {
		super(message);
	}
}
