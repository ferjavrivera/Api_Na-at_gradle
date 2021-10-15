package mx.com.naat.users.domain.service;

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

import mx.com.naat.users.domain.api.UserServicePort;
import mx.com.naat.users.domain.data.UserDto;
import mx.com.naat.users.domain.data.UserRequestBody;
import mx.com.naat.users.domain.spi.UserPersistancePort;
import mx.com.naat.users.infrastructure.entity.Users;
import mx.com.naat.users.infrastructure.exceptions.EmailExists;
import mx.com.naat.users.infrastructure.exceptions.InvalidRole;
import mx.com.naat.users.infrastructure.exceptions.UserNotFound;

public class UserServiceImpl implements UserServicePort{

	private UserPersistancePort userPersistancePort;
	
	public UserServiceImpl(UserPersistancePort userPersistancePort) {
		this.userPersistancePort = userPersistancePort;
	}

	@Override
	public List<UserDto> getAllUsers(int page, int size, String[] sortMethod) {
		
		
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
		
		return userPersistancePort.getAllUsers(paginSort);
	}

	@Override
	public Users getUserById(UUID id) {
		Optional<Users> user = userPersistancePort.getUserById(id);
		if (user.isPresent()) {
			Users userAux = user.get();
			return userAux;
		}
		throw new UserNotFound("not found");
	}

	
	@Override
	public Users putUser(UUID id, UserRequestBody userRequestBody) {
		
		Optional <Users> usuario =  userPersistancePort.getUserById(id);
		
		if(usuario.isPresent()) {
			Users user = usuario.get();
			if(userRequestBody.getEmail() !=null) {
				if((userPersistancePort.getUserByEmail(userRequestBody.getEmail()) == null) || (user.getEmail().equals(userRequestBody.getEmail()))) {
					user.setEmail(userRequestBody.getEmail());									
				}else {
					throw new EmailExists("email already exists");
				}
			}
			if(userRequestBody.getName() != null)
				user.setName(userRequestBody.getName());
			if(userRequestBody.getLastName() != null)
				user.setLastName(userRequestBody.getLastName());
			
			user.setVacationStart(userRequestBody.getVacationStart());
			user.setVacationEnd(userRequestBody.getVacationEnd());
			
			if(userRequestBody.getRole() != null) {
				if(userRequestBody.getRole().contentEquals("DEVELOPER") || userRequestBody.getRole().contentEquals("ADMINISTRATOR")) {
					user.setRole(userRequestBody.getRole());				
				}else {
					throw new InvalidRole("invalid role"); 
				}
			}
			user.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
			userPersistancePort.putUser(user);
			return user;
			
			
		}
		
		throw new UserNotFound("not_found");
		
	}

	@Override
	public long countRegisters() {
		return userPersistancePort.countRegisters();
	}

}
































