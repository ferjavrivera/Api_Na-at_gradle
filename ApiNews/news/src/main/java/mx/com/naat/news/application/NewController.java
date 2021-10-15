package mx.com.naat.news.application;


import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.naat.news.domain.api.NewServicePort;
import mx.com.naat.news.domain.data.AddNewRequestDto;
import mx.com.naat.news.domain.data.NewDetailResponseDTO;
import mx.com.naat.news.domain.data.NewDto;
import mx.com.naat.news.domain.data.NewUpdateRequestDto;

@RestController
@RequestMapping("/news")
public class NewController {

	@Autowired
	private NewServicePort newServicePort;

	@GetMapping
	public ResponseEntity<Iterable<NewDto>> getAllNews(
			@RequestParam(required = false, defaultValue = "0", value = "page") int page,
			@RequestParam(required = false, defaultValue = "20", value = "size") int size,
			@RequestParam(required = false, defaultValue = "creationDate,desc", value = "sort") String sort) {

		List<NewDto> lista = newServicePort.getNews(page, size, sort);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Total-elements", String.valueOf(lista.size()));

		return ResponseEntity.ok().headers(headers).body(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<NewDetailResponseDTO> getNewByID(@PathVariable UUID id) {
		return new ResponseEntity<>(newServicePort.getNewById(id), HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<NewDetailResponseDTO> postNew(@ModelAttribute AddNewRequestDto noticia) {
		return new ResponseEntity<>(newServicePort.addNew(noticia), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<NewDetailResponseDTO> updateNew(@PathVariable UUID id, @ModelAttribute NewUpdateRequestDto newUpdateDto) {
		return new ResponseEntity<>(newServicePort.updateNew(id, newUpdateDto), HttpStatus.OK);
	}
	
	public static UUID getUserLog() {
		return UUID.fromString("00000000-0000-0000-0000-000000000010");
	}
}
