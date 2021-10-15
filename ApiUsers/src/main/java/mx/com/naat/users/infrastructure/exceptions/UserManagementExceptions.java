package mx.com.naat.users.infrastructure.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.demo")
public class UserManagementExceptions {
	@ExceptionHandler(value = {InvalidRole.class})
	public ResponseEntity<Object> handleInvalidRole(InvalidRole ex){
		
		JsonReturn jr = new JsonReturn();
		jr.setTimeStamp(Instant.now().toEpochMilli());
		jr.setStatus(HttpStatus.BAD_REQUEST.value());
		jr.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		jr.setMessage(ex.getMessage().replace(" ", "_").toUpperCase());
		jr.setPath("/users");
		
		return new ResponseEntity<Object>(jr, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {UserNotFound.class})
	public ResponseEntity<Object> handlerUserNotFound(UserNotFound unf){
		
		JsonReturn jr = new JsonReturn();
		jr.setTimeStamp(Instant.now().toEpochMilli());
		jr.setStatus(HttpStatus.NOT_FOUND.value());
		jr.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		jr.setMessage(unf.getMessage().replace(" ", "_").toUpperCase());
		jr.setPath("/users");
		
		return new ResponseEntity<Object>(jr, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {EmailExists.class})
	public ResponseEntity<Object> handlerEmailExists(EmailExists unf){
		
		JsonReturn jr = new JsonReturn();
		jr.setTimeStamp(Instant.now().toEpochMilli());
		jr.setStatus(HttpStatus.BAD_REQUEST.value());
		jr.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		jr.setMessage(unf.getMessage().replace(" ", "_").toUpperCase());
		jr.setPath("/users");
		
		return new ResponseEntity<Object>(jr, HttpStatus.BAD_REQUEST);
	}
}
