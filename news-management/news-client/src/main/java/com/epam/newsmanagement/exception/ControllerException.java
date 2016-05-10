package com.epam.newsmanagement.exception;

public class ControllerException extends Exception{

	private static final long serialVersionUID = 1L;

	public ControllerException() {
		super();
	}

	public ControllerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}
}
