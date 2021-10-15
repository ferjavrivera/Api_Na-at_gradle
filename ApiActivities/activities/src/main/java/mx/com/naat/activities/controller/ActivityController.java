package mx.com.naat.activities.controller;

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

import mx.com.naat.activities.domain.api.ActivityServicePort;
import mx.com.naat.activities.domain.data.ActivityDetail;
import mx.com.naat.activities.domain.data.ActivityDto;
import mx.com.naat.activities.domain.data.ActivityRequestBody;

@RestController
@RequestMapping("/activities")
public class ActivityController {
	
	@Autowired
	private ActivityServicePort activityServicePort;
	
	@GetMapping
	public ResponseEntity<List<ActivityDto>> getActivities(@RequestParam(required = false, defaultValue = "0") int page, 
													@RequestParam(required = false, defaultValue = "20") int size,
													@RequestParam(required = false, defaultValue = "creationDate,desc") String[] sort){
		List<ActivityDto> activities = activityServicePort.getAllActivities(page, size, sort);
		
		HttpHeaders hr = new HttpHeaders();
		long totalElements = activityServicePort.countRegisters();
		hr.add("Total-Elements", String.valueOf(totalElements));
		
		return new ResponseEntity<>(activities, hr, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ActivityDetail> getActivityById(@PathVariable UUID id){
		ActivityDetail activity = activityServicePort.getActivityById(id);
		return new ResponseEntity<>(activity, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ActivityDetail> updateActivity(@PathVariable UUID id, @Valid @RequestBody ActivityRequestBody activityRequestBody){
		/* Esto después habrá que cambiarlo por el ID del usuario que tenga la sesion iniciada */
		UUID idAuthor = UUID.fromString("00000000-0000-0000-0000-00000000");
		activityRequestBody.setId(id);
		ActivityDetail activity = activityServicePort.putActivity( idAuthor, activityRequestBody);
		return new ResponseEntity<>(activity, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ActivityDetail> createClient(@Valid @RequestBody ActivityRequestBody activityRequestBody){
		/* Esto después habrá que cambiarlo por el ID del usuario que tenga la sesion iniciada */
		UUID idAuthor = UUID.fromString("00000000-0000-0000-0000-00000000");
		ActivityDetail activity = activityServicePort.createActivity( idAuthor, activityRequestBody);
		return new ResponseEntity<>(activity, HttpStatus.CREATED);
	}
	
}
