package com.walmart.drivers.exception;

public class InputParamException extends Exception{
    private final String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public InputParamException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
}
