package mx.com.naat.clients.domain.api;

import java.util.List;
import java.util.UUID;

import mx.com.naat.clients.domain.data.ClientDto;
import mx.com.naat.clients.domain.data.ClientDtoAux;
import mx.com.naat.clients.domain.data.ClientRequestBody;
import mx.com.naat.clients.infrastructure.entity.Clients;

public interface ClientServicePort {
	List<ClientDto> getAllClients(int page, int size, String[] sort);
	ClientDtoAux getClientById(UUID id);
	ClientDtoAux putClient(UUID id, ClientRequestBody clientsRequestBody);
	long countRegisters();
	ClientDtoAux createClient(ClientRequestBody clientsRequestBody);
}
