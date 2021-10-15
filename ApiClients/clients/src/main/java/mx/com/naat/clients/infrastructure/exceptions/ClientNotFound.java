package mx.com.naat.clients.infrastructure.exceptions;

public class ClientNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ClientNotFound(String message) {
		super(message);
	}
}

