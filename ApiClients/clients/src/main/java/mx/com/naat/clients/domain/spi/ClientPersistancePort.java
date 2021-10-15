package mx.com.naat.clients.domain.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import mx.com.naat.clients.domain.data.ClientDto;
import mx.com.naat.clients.infrastructure.entity.Clients;

public interface ClientPersistancePort {
	List<ClientDto> getAllClients(Pageable sort);
	Optional<Clients> getClientById(UUID id);
	Clients putClient(Clients client);
	long countRegisters();
	Clients createClient(Clients client);
	
	Clients getKey(String key);

	Clients getName(String name);

	Clients getDescription(String description);
}



















