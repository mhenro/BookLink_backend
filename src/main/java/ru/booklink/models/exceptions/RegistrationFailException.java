package ru.booklink.models.exceptions;

public class RegistrationFailException extends Exception {
	private static final long serialVersionUID = 6277705839489589644L;

	public RegistrationFailException(String message) {
		super(message);
	}
}
