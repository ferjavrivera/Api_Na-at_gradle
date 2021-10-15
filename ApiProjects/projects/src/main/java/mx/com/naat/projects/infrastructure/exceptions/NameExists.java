package mx.com.naat.projects.infrastructure.exceptions;

public class NameExists extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NameExists(String message) {
		super(message);
	}
}
