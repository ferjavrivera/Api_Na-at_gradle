package mx.com.naat.clients.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.com.naat.clients.domain.data.ClientDto;
import mx.com.naat.clients.infrastructure.entity.Clients;

@Mapper
public interface ClientMapper {
	ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
	ClientDto clientsToClientDto( Clients clients );
	//List<ClientDto> usersListToUserDtoList(Iterable<Clients> usersList); 
	
	List<ClientDto> clientsListToClientDtoList(Iterable<Clients> usersList); 
}
