package mx.com.naat.activities.infrastructure.exceptions;

public class UsersIdDoesntExist extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UsersIdDoesntExist(String message) {
		super(message);
	}
}

