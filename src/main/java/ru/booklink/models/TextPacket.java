package ru.booklink.models;

public class TextPacket {
	private String message;
	private HelperError error;

	public static TextPacket of(String message) {
		TextPacket result = new TextPacket();
		result.setMessage(message);

		return result;
	}

	public static TextPacket of(HelperError error) {
		TextPacket result = new TextPacket();
		result.setError(error);

		return result;
	}

	/* setters and getters */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HelperError getError() {
		return error;
	}

	public void setError(HelperError error) {
		this.error = error;
	}

}
