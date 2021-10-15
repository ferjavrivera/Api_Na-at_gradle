package mx.com.naat.clients.infrastructure.exceptions;

public class UserIdDoesntExist extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public UserIdDoesntExist(String mensaje) {
		super(mensaje);
	}

}
