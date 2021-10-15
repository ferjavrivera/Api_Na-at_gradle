package mx.com.naat.projects.domain.service;

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
import org.springframework.web.client.RestTemplate;

import mx.com.naat.projects.domain.api.ProjectsServicePort;
import mx.com.naat.projects.domain.data.ClientsAux;
import mx.com.naat.projects.domain.data.ProjectDto;
import mx.com.naat.projects.domain.data.ProjectDtoAux;
import mx.com.naat.projects.domain.data.ProjectDtoUpd;
import mx.com.naat.projects.domain.data.ProjectRequestBody;
import mx.com.naat.projects.domain.data.UsersAux;
import mx.com.naat.projects.domain.spi.ProjectsPersistancePort;
import mx.com.naat.projects.infrastructure.entity.Projects;
import mx.com.naat.projects.infrastructure.exceptions.ClientNotFound;
import mx.com.naat.projects.infrastructure.exceptions.DescriptionExists;
import mx.com.naat.projects.infrastructure.exceptions.KeyExists;
import mx.com.naat.projects.infrastructure.exceptions.NameExists;
import mx.com.naat.projects.infrastructure.exceptions.ProjectNotFound;


public class ProjectServiceImpl implements ProjectsServicePort{

	private ProjectsPersistancePort projectsPersistancePort;
	
	public ProjectServiceImpl(ProjectsPersistancePort projectsPersistancePort) {
		this.projectsPersistancePort = projectsPersistancePort;
	}
/*
	@Override
	public List<ProjectDto> getAllProjects(int page, int size, String[] sort) {
		return projectsPersistancePort.getAllProjects(page, size, sort);
	}

	@Override
	public Projects getProjectById(UUID id) {
		return projectsPersistancePort.getProjectById(id);
	}

	@Override
	public Projects putProject(Projects projects, ProjectRequestBody projectRequestBody) {
		return projectsPersistancePort.putProject(projects, projectRequestBody);
	}
	
	
	
	@Override
	public Projects createProject(ProjectRequestBody projectRequestBody) {
		return projectsPersistancePort.createProject(projectRequestBody);
	}
	*/
	@Override
	public long countRegisters() {
		return projectsPersistancePort.countRegisters();
	}

	@Override
	public List<ProjectDto> getAllProjectsByClientId(int page, int size, String[] sortMethod, UUID id) {
		if(getClientMS(id) != null) {
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
			
			List<Projects> proyectos = projectsPersistancePort.getAllProjectsByClientId(id, paginSort);
			List<ProjectDto> projects = new ArrayList<>();
			for(Projects p: proyectos) {
				ClientsAux client = getClientMS(p.getIdClient());
				projects.add(new ProjectDto(p.getId(), p.getKey(), p.getName(), p.getDescription(), client));
			}
	
			return projects;
		}
		throw new ClientNotFound("not found");
	}

	@Override
	public ProjectDtoAux getProjectByIdAndClientId(UUID id, UUID idClient) {
		
		if(getClientMS(idClient) != null) {
			Optional<Projects> project = projectsPersistancePort.getProjectByIdAndClientId(id);
			if (project.isPresent()) {
	
				UsersAux user = getUserMS(project.get().getIdAuthor());
				ClientsAux client = getClientMS(project.get().getIdClient());
				ProjectDtoAux cl = ProjectDtoAux.builder().id(project.get().getId())
						.creationDate(project.get().getCreationDate()).modificationDate(project.get().getModificationDate())
						.enabled(project.get().isEnabled()).idAuthor(user).idClient(client).key(project.get().getKey())
						.name(project.get().getName()).description(project.get().getDescription()).build();
	
				return cl;
			}
			throw new ProjectNotFound("not found");
		}
		throw new ClientNotFound("not found");
	}
	
	
	@Override
	public ProjectDtoUpd putProjectByClientId(UUID id, ProjectRequestBody projectRequestBody, UUID idClient) {
		
		if(getClientMS(idClient) != null) {
			
		
		Projects projects = projectsPersistancePort.getProjectByIdAndClientId(id).get();
		
		
		
		if(projectRequestBody.getKey() !=null) {
			
			if((projectsPersistancePort.getKey(projectRequestBody.getKey()) == null) || (projects.getKey().equals(projectRequestBody.getKey()))) {
				projects.setKey(projectRequestBody.getKey());									
			}else {
				throw new KeyExists("key already exists");
			}
		}
		if(projectRequestBody.getName() !=null) {
			
			if((projectsPersistancePort.getName(projectRequestBody.getName()) == null) || (projects.getName().equals(projectRequestBody.getName()))) {
				projects.setName(projectRequestBody.getName());									
			}else {
				throw new NameExists("name already exists");
			}
		}
		if(projectRequestBody.getDescription() !=null) {
			
			if((projectsPersistancePort.getDescription(projectRequestBody.getDescription()) == null) || (projects.getDescription().equals(projectRequestBody.getDescription()))) {
				projects.setDescription(projectRequestBody.getDescription());									
			}else {
				throw new DescriptionExists("description already exists");
			}
		}

		projects.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
		
		projectsPersistancePort.putProjectByClientId(projects);
		
		UsersAux user = getUserMS(projects.getIdAuthor());
		ProjectDtoUpd cl = ProjectDtoUpd.builder().id(projects.getId())
				.creationDate(projects.getCreationDate()).modificationDate(projects.getModificationDate())
				.enabled(projects.isEnabled()).idAuthor(user).key(projects.getKey())
				.name(projects.getName()).description(projects.getDescription()).build();
		
		
		return cl;
		}
		throw new ClientNotFound("not found");
		
		//return projectsPersistancePort.putProjectByClientId(id, projectRequestBody, idClient);
	}

	@Override
	public ProjectDtoAux createProjectByClientId(ProjectRequestBody projectRequestBody, UUID idClient) {
		if(getClientMS(idClient) != null) {
			
			Projects project = new Projects();
			if(projectRequestBody.getKey() !=null) {
				
				if(projectsPersistancePort.getKey(projectRequestBody.getKey()) == null) {
					project.setKey(projectRequestBody.getKey());									
				}else {
					throw new KeyExists("key already exists");
				}
			}
			if(projectRequestBody.getName() !=null) {
				
				if(projectsPersistancePort.getName(projectRequestBody.getName()) == null) {
					project.setName(projectRequestBody.getName());									
				}else {
					throw new NameExists("name already exists");
				}
			}
			if(projectRequestBody.getDescription() !=null) {
				
				if(projectsPersistancePort.getDescription(projectRequestBody.getDescription()) == null){
					project.setDescription(projectRequestBody.getDescription());									
				}else {
					throw new DescriptionExists("description already exists");
				}
			}
			
			
			UUID idUser = UUID.fromString("00000000-0000-0000-0000-000000000010");
			project.setIdAuthor(idUser);
			project.setIdClient(idClient);
			project.setEnabled(true);
			project.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
			project.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
			
			System.out.println(project);
			projectsPersistancePort.createProjectByClientId(project);
			
			UsersAux user = getUserMS(project.getIdAuthor());
			ClientsAux client = getClientMS(project.getIdClient());
			ProjectDtoAux cl = ProjectDtoAux.builder().id(project.getId())
					.creationDate(project.getCreationDate()).modificationDate(project.getModificationDate())
					.enabled(project.isEnabled()).key(project.getKey()).idAuthor(user).name(project.getName())
					.description(project.getDescription()).idClient(client).build();
	
			return cl;
		}
		throw new ClientNotFound("not found");
	}
	

	
	private ClientsAux getClientMS(UUID id) {
		String uri = "http://localhost:8081/clients/" + id;
		RestTemplate restTemplate = new RestTemplate();
		ClientsAux result = restTemplate.getForObject(uri, ClientsAux.class);
		return result;
	}
	
	private UsersAux getUserMS(UUID id) {
		String uri = "http://localhost:8080/users/" + id;
		RestTemplate restTemplate = new RestTemplate();
		UsersAux result = restTemplate.getForObject(uri, UsersAux.class);
		return result;
	}

}
