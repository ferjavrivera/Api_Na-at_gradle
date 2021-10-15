package mx.com.naat.clients.infrastructure.adapter;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import mx.com.naat.clients.domain.data.ClientDto;
import mx.com.naat.clients.domain.spi.ClientPersistancePort;
import mx.com.naat.clients.infrastructure.entity.Clients;
import mx.com.naat.clients.infrastructure.mapper.ClientMapper;
import mx.com.naat.clients.infrastructure.repository.ClientsRepository;

@Service
public class ClientJpaAdapter implements ClientPersistancePort{

	@Autowired
	private ClientsRepository clientsRepository;
	
	@Override
	public List<ClientDto> getAllClients(Pageable paginSort) {
		//return ClientMapper.INSTANCE.usersListToUserDtoList(clientsRepository.findAll(paginSort));
		return ClientMapper.INSTANCE.clientsListToClientDtoList(clientsRepository.findAll(paginSort));
	}

	@Override
	public Optional <Clients> getClientById(UUID id) {
		return clientsRepository.findById(id) ;
	}

	@Override
	public Clients putClient(Clients client) {

		return clientsRepository.save(client);
	
	}
	
	@Override
	public Clients getKey(String key) {
		return clientsRepository.findByKeyIgnoreCase(key);
	}
	
	
	@Override
	public Clients getName(String name) {
		return clientsRepository.findByNameIgnoreCase(name);
	}
	
	
	@Override
	public Clients getDescription(String description) {
		return clientsRepository.findByDescriptionIgnoreCase(description);	
	}
	

	@Override
	public Clients createClient(Clients client) {
		return clientsRepository.save(client);
	}

	@Override
	public long countRegisters() {
		return clientsRepository.count();
	}

	
}
