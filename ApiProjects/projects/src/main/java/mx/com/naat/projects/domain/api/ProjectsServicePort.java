package mx.com.naat.projects.domain.api;

import java.util.List;
import java.util.UUID;

import mx.com.naat.projects.domain.data.ProjectDto;
import mx.com.naat.projects.domain.data.ProjectDtoAux;
import mx.com.naat.projects.domain.data.ProjectDtoUpd;
import mx.com.naat.projects.domain.data.ProjectRequestBody;

public interface ProjectsServicePort {
	//PROJECTS BY CLIENT ID
	List<ProjectDto> getAllProjectsByClientId(int page, int size, String[] sort, UUID id);
	
	//ALL PROJECTS
	//List<ProjectDto> getAllProjects(int page, int size, String[] sort);
	
	//PROJECT BY ID
	//Projects getProjectById(UUID id);
	
	//PROJECT BY ID AND CLIENT ID
	ProjectDtoAux getProjectByIdAndClientId(UUID id, UUID idClient);
	
	//UPDATE PROJECT
	//Projects putProject(Projects project, ProjectRequestBody projectRequestBody);
	
	//UPDATE PROJECT
	ProjectDtoUpd putProjectByClientId(UUID id, ProjectRequestBody projectRequestBody, UUID idClient);
	
	//CREATE PROJECT
	//Projects createProject(ProjectRequestBody projectRequestBody);
	
	//CREATE PROJECT BY CLIENT ID
	ProjectDtoAux createProjectByClientId(ProjectRequestBody projectRequestBody, UUID idClient);
	
	long countRegisters();
	
}
