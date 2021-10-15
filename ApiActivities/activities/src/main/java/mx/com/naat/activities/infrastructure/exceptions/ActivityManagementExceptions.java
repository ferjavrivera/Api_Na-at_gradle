package mx.com.naat.activities.infrastructure.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.mx.naat.activityrecords")
public class ActivityManagementExceptions {
	@ExceptionHandler(value = {KeyExists.class})
	public ResponseEntity<Object> handlerKeyExists(KeyExists ex){
		
		JsonReturn jr = new JsonReturn();
		jr.setTimestamp(Instant.now().toEpochMilli());
		jr.setStatus(HttpStatus.BAD_REQUEST.value());
		jr.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		jr.setMessage(ex.getMessage().replace(" ", "_").toUpperCase());
		jr.setPath("/activities");
		
		return new ResponseEntity<Object>(jr, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {ActivityNotFound.class})
	public ResponseEntity<Object> handlerClientNotFound(ActivityNotFound unf){
		
		JsonReturn jr = new JsonReturn();
		jr.setTimestamp(Instant.now().toEpochMilli());
		jr.setStatus(HttpStatus.NOT_FOUND.value());
		jr.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		jr.setMessage(unf.getMessage().replace(" ", "_").toUpperCase());
		jr.setPath("/activities");
		
		return new ResponseEntity<Object>(jr, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value = {UsersIdDoesntExist.class})
	public ResponseEntity<Object> handlerClientNotFound(UsersIdDoesntExist unf){
		
		JsonReturn jr = new JsonReturn();
		jr.setTimestamp(Instant.now().toEpochMilli());
		jr.setStatus(HttpStatus.NOT_FOUND.value());
		jr.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		jr.setMessage(unf.getMessage().replace(" ", "_").toUpperCase());
		jr.setPath("/activities");
		
		return new ResponseEntity<Object>(jr, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {NameExists.class})
	public ResponseEntity<Object> handlerNameExists(NameExists unf){
		
		JsonReturn jr = new JsonReturn();
		jr.setTimestamp(Instant.now().toEpochMilli());
		jr.setStatus(HttpStatus.BAD_REQUEST.value());
		jr.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		jr.setMessage(unf.getMessage().replace(" ", "_").toUpperCase());
		jr.setPath("/activities");
		
		return new ResponseEntity<Object>(jr, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {DescriptionExists.class})
	public ResponseEntity<Object> handlerDescriptionExists(DescriptionExists unf){
		
		JsonReturn jr = new JsonReturn();
		jr.setTimestamp(Instant.now().toEpochMilli());
		jr.setStatus(HttpStatus.BAD_REQUEST.value());
		jr.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		jr.setMessage(unf.getMessage().replace(" ", "_").toUpperCase());
		jr.setPath("/activities");
		
		return new ResponseEntity<Object>(jr, HttpStatus.BAD_REQUEST);
	}
}
