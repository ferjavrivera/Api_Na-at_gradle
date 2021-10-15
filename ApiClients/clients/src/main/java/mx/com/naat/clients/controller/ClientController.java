package mx.com.naat.clients.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.naat.clients.domain.api.ClientServicePort;
import mx.com.naat.clients.domain.data.ClientDto;
import mx.com.naat.clients.domain.data.ClientDtoAux;
import mx.com.naat.clients.domain.data.ClientRequestBody;
import mx.com.naat.clients.infrastructure.entity.Clients;
import mx.com.naat.clients.infrastructure.exceptions.ClientNotFound;

@RestController
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	private ClientServicePort clientServicePort;
	
	@GetMapping
	public ResponseEntity<List<ClientDto>> getClients(@RequestParam(required = false, defaultValue = "0") int page, 
													@RequestParam(required = false, defaultValue = "20") int size,
													@RequestParam(required = false, defaultValue = "creationDate,desc") String[] sort){
		List<ClientDto> clientes = clientServicePort.getAllClients(page, size, sort);
		
		HttpHeaders hr = new HttpHeaders();
		long totalElements = clientServicePort.countRegisters();
		hr.add("Total-Elements", String.valueOf(totalElements));
		
		return new ResponseEntity<>(clientes, hr, HttpStatus.OK);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<ClientDtoAux> getClientById(@PathVariable UUID id){
			return new ResponseEntity<>(clientServicePort.getClientById(id), HttpStatus.OK);

	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClientDtoAux> updateClient(@PathVariable UUID id, @Valid @RequestBody ClientRequestBody clientRequestBody){
			return new ResponseEntity<>(clientServicePort.putClient(id, clientRequestBody), HttpStatus.OK);

		
	}
	
	@PostMapping
	public ResponseEntity<ClientDtoAux> createClient(@Valid @RequestBody ClientRequestBody clientRequestBody){
		return new ResponseEntity<>(clientServicePort.createClient(clientRequestBody), HttpStatus.CREATED);
	}
}
