package mx.com.naat.projects.infrastructure.exceptions;

public class UserIdDoesntExist extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserIdDoesntExist(String message) {
		super(message);
	}
}

