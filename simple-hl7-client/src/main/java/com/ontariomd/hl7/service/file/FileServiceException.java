package com.ontariomd.hl7.service.file;

public class FileServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileServiceException(String message) {
		super(message);
	}

}
