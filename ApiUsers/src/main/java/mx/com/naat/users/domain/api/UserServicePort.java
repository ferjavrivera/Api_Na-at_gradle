package mx.com.naat.users.domain.api;

import java.util.List;
import java.util.UUID;

import mx.com.naat.users.domain.data.UserDto;
import mx.com.naat.users.domain.data.UserRequestBody;
import mx.com.naat.users.infrastructure.entity.Users;

public interface UserServicePort {
	List<UserDto> getAllUsers(int page, int size, String[] sort);
	Users getUserById(UUID id);
	Users putUser(UUID id, UserRequestBody userRequestBody);
	long countRegisters();
}
