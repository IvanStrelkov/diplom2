package com.epam.newsmanagement.exception;

/**
 * The Class ServiceException.
 */
public class ServiceException extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Constructor.
	 */
	public ServiceException() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 */
	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * The Constructor.
	 *
	 * @param arg0 the arg0
	 */
	public ServiceException(String arg0) {
		super(arg0);
	}

	/**
	 * The Constructor.
	 *
	 * @param arg0 the arg0
	 */
	public ServiceException(Throwable arg0) {
		super(arg0);
	}
}
