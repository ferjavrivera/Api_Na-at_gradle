package mx.com.naat.news.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHelper {

	@ExceptionHandler(value = {SummaryAlreadyExistsException.class})
	public ResponseEntity<Object> handlerInvalidRoleException(SummaryAlreadyExistsException ex){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
		exceptionResponse.setStatus(400);
		exceptionResponse.setError("Bad Request");
		exceptionResponse.setMessage("SUMMARY_ALREADY_EXISTS");
		exceptionResponse.setPath("/news");
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {BodyAlreadyExistsException.class})
	public ResponseEntity<Object> handlerNotFoundUserIdException(BodyAlreadyExistsException ex){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
		exceptionResponse.setStatus(400);
		exceptionResponse.setError("Bad Request");
		exceptionResponse.setMessage("BODY_ALREADY_EXISTS");
		exceptionResponse.setPath("/news");
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {HeadlineAlreadyExistsException.class})
	public ResponseEntity<Object> handlerEmailAlreadyExistsException(HeadlineAlreadyExistsException ex){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
		exceptionResponse.setStatus(400);
		exceptionResponse.setError("Bad Request");
		exceptionResponse.setMessage("HEADLINE_ALREADY_EXISTS");
		exceptionResponse.setPath("/news");
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {NotFoundNewsIdException.class})
	public ResponseEntity<Object> handlerNotFoundClientIdException(NotFoundNewsIdException ex){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
		exceptionResponse.setStatus(404);
		exceptionResponse.setError("Not Found");
		exceptionResponse.setMessage("NOT_FOUND");
		exceptionResponse.setPath("/news");
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {ImageContentTypeException.class})
	public ResponseEntity<Object> handlerImageContentTypeException(ImageContentTypeException ex){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
		exceptionResponse.setStatus(404);
		exceptionResponse.setError("Bad request");
		exceptionResponse.setMessage("CONTENT_TYPE_UNSUPPORTED");
		exceptionResponse.setPath("/news");
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {ConexionException.class})
	public ResponseEntity<Object> handlerConexionException(ConexionException ex){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
		exceptionResponse.setStatus(503);
		exceptionResponse.setError("Service Unavailable");
		exceptionResponse.setMessage("SERVICE_UNAVAILABLE");
		exceptionResponse.setPath("/news");
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
