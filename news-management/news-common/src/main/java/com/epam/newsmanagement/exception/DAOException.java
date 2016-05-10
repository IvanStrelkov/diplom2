package com.epam.newsmanagement.exception;

/**
 * The Class DAOException.
 */
public class DAOException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Constructor.
	 */
	public DAOException() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 */
	public DAOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * The Constructor.
	 *
	 * @param message the message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * The Constructor.
	 *
	 * @param cause the cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}

}
