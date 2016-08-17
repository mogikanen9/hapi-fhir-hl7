package com.ontariomd.hl7.service.message;

public class MessageServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public MessageServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageServiceException(String message) {
		super(message);
	}

}
