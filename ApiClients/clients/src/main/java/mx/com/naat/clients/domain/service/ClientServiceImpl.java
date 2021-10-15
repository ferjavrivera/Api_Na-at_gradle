package mx.com.naat.clients.domain.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.client.RestTemplate;

import mx.com.naat.clients.domain.api.ClientServicePort;
import mx.com.naat.clients.domain.data.ClientDto;
import mx.com.naat.clients.domain.data.ClientDtoAux;
import mx.com.naat.clients.domain.data.UsersDtoAux;
import mx.com.naat.clients.domain.spi.ClientPersistancePort;
import mx.com.naat.clients.domain.data.ClientRequestBody;
import mx.com.naat.clients.domain.data.UsersAux;
import mx.com.naat.clients.infrastructure.entity.Clients;
import mx.com.naat.clients.infrastructure.exceptions.ClientNotFound;
import mx.com.naat.clients.infrastructure.exceptions.DescriptionExists;
import mx.com.naat.clients.infrastructure.exceptions.KeyExists;
import mx.com.naat.clients.infrastructure.exceptions.NameExists;
import mx.com.naat.clients.infrastructure.exceptions.UserIdDoesntExist;


public class ClientServiceImpl implements ClientServicePort{

	
	private ClientPersistancePort clientPersistancePort;
	
	public ClientServiceImpl(ClientPersistancePort clientPersistancePort) {
		this.clientPersistancePort = clientPersistancePort;
	}
	
	@Override
	public List<ClientDto> getAllClients(int page, int size, String[] sortMethod) {
		List<Order> orders = new ArrayList<Order>();
		if(sortMethod.length > 1) {
			if(sortMethod[1].equals("desc")) {
				orders.add(new Order(Sort.Direction.DESC, sortMethod[0]));
			}else {
				orders.add(new Order(Sort.Direction.ASC, sortMethod[0]));
			}
		}else {
			orders.add(new Order(Sort.Direction.ASC,sortMethod[0]));
		}
		
		Pageable paginSort = PageRequest.of(page, size, Sort.by(orders));
		
		
		return clientPersistancePort.getAllClients(paginSort);
		
	}

	@Override
	public ClientDtoAux getClientById(UUID id) {
		Optional<Clients> client = clientPersistancePort.getClientById(id);
		
		
		if (client.isPresent()) {

			UsersAux user = getUserMS(client.get().getIdAuthor());
			ClientDtoAux cl = ClientDtoAux.builder().id(client.get().getId())
					.creationDate(client.get().getCreationDate()).modificationDate(client.get().getModificationDate())
					.enabled(client.get().isEnabled()).idAuthor(user).key(client.get().getKey())
					.name(client.get().getName()).description(client.get().getDescription()).build();

			return cl;
		}
		throw new ClientNotFound("not found");
	}

	@Override
	public ClientDtoAux putClient(UUID id, ClientRequestBody clientRequestBody) {

		Optional<Clients> cliente = clientPersistancePort.getClientById(id);
		Clients client = cliente.get();
		if(clientRequestBody.getKey() !=null) {
			if((clientPersistancePort.getKey(clientRequestBody.getKey()) == null) || (client.getKey().equals(clientRequestBody.getKey()))) {
				client.setKey(clientRequestBody.getKey());									
			}else {
				throw new KeyExists("key already exists");
			}
		}
		if(clientRequestBody.getName() !=null) {
			if((clientPersistancePort.getName(clientRequestBody.getName()) == null) || (client.getName().equals(clientRequestBody.getName()))) {
				client.setName(clientRequestBody.getName());									
			}else {
				throw new NameExists("name already exists");
			}
		}
		if(clientRequestBody.getDescription() !=null) {
			if((clientPersistancePort.getDescription(clientRequestBody.getDescription()) == null) || (client.getDescription().equals(clientRequestBody.getDescription()))) {
				client.setDescription(clientRequestBody.getDescription());									
			}else {
				throw new DescriptionExists("description already exists");
			}
		}
		client.setEnabled(true);
		client.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
		Clients cliente1 = clientPersistancePort.putClient(client);
		UsersAux user = getUserMS(cliente1.getIdAuthor());
		ClientDtoAux cl = ClientDtoAux.builder().id(cliente1.getId())
				.creationDate(cliente1.getCreationDate()).modificationDate(cliente1.getModificationDate())
				.enabled(cliente1.isEnabled()).idAuthor(user).key(cliente1.getKey())
				.name(cliente1.getName()).description(cliente1.getDescription()).build();

		return cl;
	}

	@Override
	public ClientDtoAux createClient(ClientRequestBody clientRequestBody) {
		Clients client = new Clients();
		if(clientRequestBody.getKey() !=null) {
			
			if(clientPersistancePort.getKey(clientRequestBody.getKey()) == null) {
				client.setKey(clientRequestBody.getKey());									
			}else {
				throw new KeyExists("key already exists");
			}
		}
		if(clientRequestBody.getName() !=null) {
			
			if(clientPersistancePort.getName(clientRequestBody.getName()) == null) {
				client.setName(clientRequestBody.getName());									
			}else {
				throw new NameExists("name already exists");
			}
		}
		if(clientRequestBody.getDescription() !=null) {
			
			if(clientPersistancePort.getDescription(clientRequestBody.getDescription()) == null){
				client.setDescription(clientRequestBody.getDescription());									
			}else {
				throw new DescriptionExists("description already exists");
			}
		}
		
		RestTemplate restTemplate = new RestTemplate();
		UUID id  = UUID.fromString("00000000-0000-0000-0000-000000000010");
		UsersDtoAux userId = restTemplate.getForObject("http://localhost:8080/users/"+id, UsersDtoAux.class);
		if( userId != null) {
			client.setIdAuthor(id);
		}else {
			throw new UserIdDoesntExist("el id de usuario no existe");
		}
		
		client.setEnabled(true);
		client.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
		client.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
		Clients cliente1 = clientPersistancePort.createClient(client);
		UsersAux user = getUserMS(cliente1.getIdAuthor());
		ClientDtoAux cl = ClientDtoAux.builder().id(cliente1.getId())
				.creationDate(cliente1.getCreationDate()).modificationDate(cliente1.getModificationDate())
				.enabled(cliente1.isEnabled()).idAuthor(user).key(cliente1.getKey())
				.name(cliente1.getName()).description(cliente1.getDescription()).build();

		return cl;
	}

	@Override
	public long countRegisters() {
		return clientPersistancePort.countRegisters();
	}

	public UsersAux getUserMS(UUID id) {
		String uri = "http://localhost:8080/users/" + id;
		RestTemplate restTemplate = new RestTemplate();
		UsersAux result = restTemplate.getForObject(uri, UsersAux.class);
		return result;
	}
	
}
