package mx.com.naat.users.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mx.com.naat.users.infrastructure.entity.Users;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<Users, UUID>{
	Users findByEmailIgnoreCase(String email);
}
