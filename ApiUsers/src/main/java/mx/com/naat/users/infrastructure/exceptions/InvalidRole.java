package mx.com.naat.users.infrastructure.exceptions;

public class InvalidRole extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidRole(String message) {
		super(message);
	}
}
