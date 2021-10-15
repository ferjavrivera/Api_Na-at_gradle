package mx.com.naat.clients.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.naat.clients.domain.api.ClientServicePort;
import mx.com.naat.clients.domain.service.ClientServiceImpl;
import mx.com.naat.clients.domain.spi.ClientPersistancePort;
import mx.com.naat.clients.infrastructure.adapter.ClientJpaAdapter;

@Configuration
public class ClientConfig {

	@Bean
	public ClientPersistancePort clientPersistance() {
		return new ClientJpaAdapter();
	}
	
	@Bean
	public ClientServicePort clientServicePort() {
		return new ClientServiceImpl(clientPersistance());
	}
}
