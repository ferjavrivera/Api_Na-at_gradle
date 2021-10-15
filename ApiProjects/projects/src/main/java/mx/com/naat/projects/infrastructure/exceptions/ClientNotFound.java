package mx.com.naat.projects.infrastructure.exceptions;

public class ClientNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ClientNotFound(String message) {
		super(message);
	}
}
