package mx.com.naat.users.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.naat.users.domain.api.UserServicePort;
import mx.com.naat.users.domain.service.UserServiceImpl;
import mx.com.naat.users.domain.spi.UserPersistancePort;
import mx.com.naat.users.infrastructure.adapter.UserJpaAdapter;

@Configuration
public class UserConfig {

	@Bean
	public UserPersistancePort userPersistance() {
		return new UserJpaAdapter();
	}
	
	@Bean
	public UserServicePort userServicePort() {
		return new UserServiceImpl(userPersistance());
	}
}
