package mx.com.naat.news.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.naat.news.domain.api.NewServicePort;
import mx.com.naat.news.domain.service.NewServiceImp;
import mx.com.naat.news.domain.spi.NewPersistencePort;
import mx.com.naat.news.infrastructure.adapter.NewJpaAdapter;


@Configuration
public class NewConfig {

	
	@Bean
    public NewPersistencePort newPersistence(){
        return new NewJpaAdapter();
    }

	 @Bean
	    public NewServicePort userService(){
	        return new NewServiceImp(newPersistence());
	    }
}
