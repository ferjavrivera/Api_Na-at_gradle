package mx.com.naat.projects.infrastructure.exceptions;

public class ProjectNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ProjectNotFound(String message) {
		super(message);
	}
}

