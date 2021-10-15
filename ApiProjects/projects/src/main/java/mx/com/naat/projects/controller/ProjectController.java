package mx.com.naat.projects.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.naat.projects.domain.api.ProjectsServicePort;
import mx.com.naat.projects.domain.data.ProjectDto;
import mx.com.naat.projects.domain.data.ProjectDtoAux;
import mx.com.naat.projects.domain.data.ProjectDtoUpd;
import mx.com.naat.projects.domain.data.ProjectRequestBody;

@RestController
@RequestMapping("/clients/{idClient}/projects")
public class ProjectController {
	
	@Autowired
	private ProjectsServicePort projectsServicePort;
	
	@GetMapping
	public ResponseEntity<List<ProjectDto>> getProjectsByClientId( @PathVariable UUID idClient,
													@RequestParam(required = false, defaultValue = "0") int page, 
													@RequestParam(required = false, defaultValue = "20") int size,
													@RequestParam(required = false, defaultValue = "creationDate,desc") String[] sort){
		
		List<ProjectDto> projects = projectsServicePort.getAllProjectsByClientId(page, size, sort, idClient);
		
		HttpHeaders hr = new HttpHeaders();
		long totalElements = projectsServicePort.countRegisters();
		hr.add("Total-Elements", String.valueOf(totalElements));
		
		return new ResponseEntity<>(projects, hr, HttpStatus.OK);
	}
	/*
	@GetMapping()
	public ResponseEntity<List<ProjectDto>> getProjects(
													@RequestParam(required = false, defaultValue = "0") int page, 
													@RequestParam(required = false, defaultValue = "20") int size,
													@RequestParam(required = false, defaultValue = "creationDate,desc") String[] sort){
		List<ProjectDto> projects = projectsServicePort.getAllProjects(page, size, sort);
		
		HttpHeaders hr = new HttpHeaders();
		long totalElements = projectsServicePort.countRegisters();
		hr.add("Total-Elements", String.valueOf(totalElements));
		
		return new ResponseEntity<>(projects, hr, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Projects> getProjectById(@PathVariable UUID id){
		Projects project = projectsServicePort.getProjectById(id);
		if(project != null)
			return new ResponseEntity<>(project, HttpStatus.OK);
		else
			throw new ProjectNotFound("not found");
	}
	*/
	
	@GetMapping("/{id}")
	public ResponseEntity<ProjectDtoAux> getProjectByIdAndClientId(@PathVariable UUID id, @PathVariable UUID idClient){
			return new ResponseEntity<>(projectsServicePort.getProjectByIdAndClientId(id, idClient), HttpStatus.OK);
	}
	
	/*
	@PutMapping("/{id}")
	public ResponseEntity<Projects> updateProject(@PathVariable UUID id, @Valid @RequestBody ProjectRequestBody projectRequestBody){
		Projects project = projectsServicePort.getProjectById(id);
		if(project != null) {
			project = projectsServicePort.putProject(project, projectRequestBody);
			return new ResponseEntity<>(project, HttpStatus.OK);
		}
			
		throw new ProjectNotFound("not found");
		
	}
	*/
	@PutMapping("/{id}")
	public ResponseEntity<ProjectDtoUpd> updateProjectByClientId(@PathVariable UUID id, @Valid @RequestBody ProjectRequestBody projectRequestBody, @PathVariable UUID idClient){
		return new ResponseEntity<>(projectsServicePort.putProjectByClientId(id, projectRequestBody, idClient), HttpStatus.OK);
		
	}
	
	/*
	@PostMapping
	public ResponseEntity<Projects> createProject(@Valid @RequestBody ProjectRequestBody projectRequestBody){
		
		Projects project = projectsServicePort.createProject(projectRequestBody);
		return new ResponseEntity<>(project, HttpStatus.CREATED);
	}
	*/
	
	@PostMapping
	public ResponseEntity<ProjectDtoAux> createProjectByClientId(@Valid @RequestBody ProjectRequestBody projectRequestBody, @PathVariable UUID idClient){
		return new ResponseEntity<>(projectsServicePort.createProjectByClientId(projectRequestBody, idClient), HttpStatus.CREATED);
	}
	
}
