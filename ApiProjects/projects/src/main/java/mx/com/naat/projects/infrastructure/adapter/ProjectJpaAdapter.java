package mx.com.naat.projects.infrastructure.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.com.naat.projects.domain.spi.ProjectsPersistancePort;
import mx.com.naat.projects.infrastructure.entity.Projects;
import mx.com.naat.projects.infrastructure.repository.ProjectsRepository;

@Service
public class ProjectJpaAdapter implements ProjectsPersistancePort{

	@Autowired
	private ProjectsRepository projectsRepository;
	
	@Override
	public List<Projects> getAllProjectsByClientId(UUID id, Pageable paginSort) {
		List<Projects> projects = new ArrayList<>();
		Iterable<Projects> listaProjects = projectsRepository.findAllByIdClient(id, paginSort);
		listaProjects.forEach(projects::add);
		return projects;
	}

	/*
	@Override
	public List<ProjectDto> getAllProjects(int page, int size, String[] sortMethod) {
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
		
		
		return ProjectsMapper.INSTANCE.projectsListToProjectDtoList(projectsRepository.findAll(paginSort));
		
	}

	@Override
	public Projects getProjectById(UUID id) {
		
		Optional<Projects> project = projectsRepository.findById(id);
		
		return (project.isPresent()) ? project.get() : null;
	}
	
	*/
	@Override
	public Optional<Projects> getProjectByIdAndClientId(UUID id) {
		return projectsRepository.findById(id);
		
	}
/*
	@Override
	public Projects putProject(Projects projects, ProjectRequestBody projectRequestBody) {
		if(projectRequestBody.getKey() !=null) {
			
			if((projectsRepository.findByKeyIgnoreCase(projectRequestBody.getKey()) == null) || (projects.getKey().equals(projectRequestBody.getKey()))) {
				projects.setKey(projectRequestBody.getKey());									
			}else {
				throw new KeyExists("key already exists");
			}
		}
		if(projectRequestBody.getName() !=null) {
			
			if((projectsRepository.findByNameIgnoreCase(projectRequestBody.getName()) == null) || (projects.getName().equals(projectRequestBody.getName()))) {
				projects.setName(projectRequestBody.getName());									
			}else {
				throw new NameExists("name already exists");
			}
		}
		if(projectRequestBody.getDescription() !=null) {
			
			if((projectsRepository.findByDescriptionIgnoreCase(projectRequestBody.getDescription()) == null) || (projects.getDescription().equals(projectRequestBody.getDescription()))) {
				projects.setDescription(projectRequestBody.getDescription());									
			}else {
				throw new DescriptionExists("description already exists");
			}
		}

		projects.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
		projectsRepository.save(projects);
		return projects;
	}
	*/
	@Override
	public void putProjectByClientId(Projects projects) {
		projectsRepository.save(projects);
		
	}
	
	@Override
	public Projects getKey(String key) {
		return projectsRepository.findByKeyIgnoreCase(key);
	}
	@Override
	public Projects getName(String name) {
		return projectsRepository.findByNameIgnoreCase(name);
	}
	@Override
	public Projects getDescription(String description) {
		return projectsRepository.findByDescriptionIgnoreCase(description);
	}
	/*
	@Override
	public Projects createProject(ProjectRequestBody projectRequestBody) {
		Projects project = new Projects();
		if(projectRequestBody.getKey() !=null) {
			
			if(projectsRepository.findByKeyIgnoreCase(projectRequestBody.getKey()) == null) {
				project.setKey(projectRequestBody.getKey());									
			}else {
				throw new KeyExists("key already exists");
			}
		}
		if(projectRequestBody.getName() !=null) {
			
			if(projectsRepository.findByNameIgnoreCase(projectRequestBody.getName()) == null) {
				project.setName(projectRequestBody.getName());									
			}else {
				throw new NameExists("name already exists");
			}
		}
		if(projectRequestBody.getDescription() !=null) {
			
			if(projectsRepository.findByDescriptionIgnoreCase(projectRequestBody.getDescription()) == null){
				project.setDescription(projectRequestBody.getDescription());									
			}else {
				throw new DescriptionExists("description already exists");
			}
		}
		
		RestTemplate restTemplate = new RestTemplate();
		UUID idUser = UUID.fromString("91c22a4b-1210-4778-8352-12b7852fc016");
		if(restTemplate.getForObject("http://localhost:8080/users/"+idUser, UsersDtoAux.class) != null) {
			project.setNameAuthor(idUser);
		}else {
			throw new UserIdDoesntExist("El id de usuario no existe");
		}
		

		project.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
		projectsRepository.save(project);
		return project;
	}
	*/

	@Override
	public void createProjectByClientId(Projects projects) {
		projectsRepository.save(projects);
	}

	@Override
	public long countRegisters() {
		return projectsRepository.count();
	}
	


}
