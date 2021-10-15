package mx.com.naat.projects.domain.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import mx.com.naat.projects.infrastructure.entity.Projects;

public interface ProjectsPersistancePort {
	//PROJECTS BY CLIENT ID
	List<Projects> getAllProjectsByClientId(UUID id, Pageable paginSort);
	
	//ALL PROJECTS
	//List<ProjectDto> getAllProjects(int page, int size, String[] sort);
	
	//PROJECT BY ID
	//Projects getProjectById(UUID id);
	
	//PROJECT BY ID AND CLIENT ID
	Optional<Projects> getProjectByIdAndClientId(UUID id);
	
	//UPDATE PROJECT
	//Projects putProject(Projects project, ProjectRequestBody projectRequestBody);
	
	//UPDATE PROJECT
	void putProjectByClientId(Projects project);

	//CREATE PROJECT
	//Projects createProject(ProjectRequestBody projectRequestBody);
	
	//CREATE PROJECT BY CLIENT ID
	void createProjectByClientId(Projects project);
	
	
	Projects getKey(String key);
	
	Projects getName(String name);
	
	Projects getDescription(String description);
	
	long countRegisters();
}
