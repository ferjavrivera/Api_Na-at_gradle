package mx.com.naat.activities.infrastructure.exceptions;

public class ActivityNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ActivityNotFound(String message) {
		super(message);
	}
}

