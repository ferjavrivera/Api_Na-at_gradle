package mx.com.naat.clients.infrastructure.exceptions;

public class DescriptionExists extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DescriptionExists(String message) {
		super(message);
	}
}
