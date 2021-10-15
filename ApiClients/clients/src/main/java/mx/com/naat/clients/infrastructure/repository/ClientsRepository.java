package mx.com.naat.clients.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mx.com.naat.clients.infrastructure.entity.Clients;

@Repository
public interface ClientsRepository extends PagingAndSortingRepository<Clients, UUID>{
	Clients findByKeyIgnoreCase(String email);
	Clients findByNameIgnoreCase(String name);
	Clients findByDescriptionIgnoreCase(String description);
}
