package com.winit.framework.exception;

public class DaoException extends RuntimeException {
	private static final long serialVersionUID = 4808330779417795427L;

	public DaoException() {
		super();
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
