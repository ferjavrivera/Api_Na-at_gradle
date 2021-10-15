package mx.com.naat.users.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.com.naat.users.domain.data.UserDto;
import mx.com.naat.users.infrastructure.entity.Users;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	List<UserDto> usersListToUserDtoList(Iterable<Users> usersList); 
	//UserDto usersToUserDto(Users value);
}
