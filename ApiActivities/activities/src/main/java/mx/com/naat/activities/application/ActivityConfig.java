package mx.com.naat.activities.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.naat.activities.domain.api.ActivityServicePort;
import mx.com.naat.activities.domain.service.ActivityServiceImpl;
import mx.com.naat.activities.domain.spi.ActivityPersistancePort;
import mx.com.naat.activities.domain.spi.UserPersistencePort;
import mx.com.naat.activities.infrastructure.adapter.ActivityJpaAdapter;
import mx.com.naat.activities.infrastructure.adapter.UserAdapter;

@Configuration
public class ActivityConfig {

	@Bean
	public ActivityPersistancePort activityPersistance() {
		return new ActivityJpaAdapter();
	}
	
	@Bean
	public UserPersistencePort userPersistance() {
		return new UserAdapter();
	}

	@Bean
	public ActivityServicePort activityServicePort() {
		return new ActivityServiceImpl(activityPersistance(), userPersistance());
	}
}
