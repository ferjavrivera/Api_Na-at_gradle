package mx.com.naat.users.infrastructure.adapter;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import mx.com.naat.users.domain.data.UserDto;
import mx.com.naat.users.domain.spi.UserPersistancePort;
import mx.com.naat.users.infrastructure.entity.Users;
import mx.com.naat.users.infrastructure.mapper.UserMapper;
import mx.com.naat.users.infrastructure.repository.UsersRepository;

@Service
public class UserJpaAdapter implements UserPersistancePort{

	@Autowired
	private UsersRepository usersRepository;
	
	
	@Override
	public List<UserDto> getAllUsers(Pageable paginSort) {
		return UserMapper.INSTANCE.usersListToUserDtoList(usersRepository.findAll(paginSort));
	}

	@Override
	public Optional<Users> getUserById(UUID id) {
		return usersRepository.findById(id);
	}

	@Override
	public void putUser(Users user) {
		usersRepository.save(user);
	}
	
	//nuevo
	@Override
	public Users getUserByEmail (String email) {
		
		return usersRepository.findByEmailIgnoreCase(email);
		
	}
	

	@Override
	public long countRegisters() {
		return usersRepository.count();
	}

}
