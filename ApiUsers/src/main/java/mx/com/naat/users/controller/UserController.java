package mx.com.naat.users.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.naat.users.domain.api.UserServicePort;
import mx.com.naat.users.domain.data.UserDto;
import mx.com.naat.users.domain.data.UserRequestBody;
import mx.com.naat.users.infrastructure.entity.Users;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserServicePort userServicePort;
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false, defaultValue = "0") int page, 
													@RequestParam(required = false, defaultValue = "20") int size,
													@RequestParam(required = false, defaultValue = "creationDate,desc") String[] sort){
		List<UserDto> usuarios = userServicePort.getAllUsers(page, size, sort);
		
		HttpHeaders hr = new HttpHeaders();
		long totalElements = userServicePort.countRegisters();
		hr.add("Total-Elements", String.valueOf(totalElements));
		
		return new ResponseEntity<>(usuarios, hr, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable UUID id){
		return new ResponseEntity<>(userServicePort.getUserById(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Users> updateUser(@PathVariable UUID id, @Valid @RequestBody UserRequestBody userRequestBody){
			return new ResponseEntity<>(userServicePort.putUser(id, userRequestBody), HttpStatus.OK);
			
	}
	

	
	
	
}
