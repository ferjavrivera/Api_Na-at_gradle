package mx.com.naat.projects.infrastructure.exceptions;

public class KeyExists extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public KeyExists(String message) {
		super(message);
	}
}
