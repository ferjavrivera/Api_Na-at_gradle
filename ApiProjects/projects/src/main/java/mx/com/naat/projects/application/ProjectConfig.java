package mx.com.naat.projects.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.naat.projects.domain.api.ProjectsServicePort;
import mx.com.naat.projects.domain.service.ProjectServiceImpl;
import mx.com.naat.projects.domain.spi.ProjectsPersistancePort;
import mx.com.naat.projects.infrastructure.adapter.ProjectJpaAdapter;

@Configuration
public class ProjectConfig {

	@Bean
	public ProjectsPersistancePort projectPersistance() {
		return new ProjectJpaAdapter();
	}
	
	@Bean
	public ProjectsServicePort activityServicePort() {
		return new ProjectServiceImpl(projectPersistance());
	}
}
