package mx.com.naat.users.domain.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import mx.com.naat.users.domain.data.UserDto;
import mx.com.naat.users.infrastructure.entity.Users;

public interface UserPersistancePort {
	List<UserDto> getAllUsers(Pageable paginSort);
	Optional <Users> getUserById(UUID id);
	void putUser(Users u);
	long countRegisters();
	
	
	//nuevo
	Users getUserByEmail(String email);
}
