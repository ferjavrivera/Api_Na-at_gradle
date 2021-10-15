package mx.com.naat.news.domain.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import mx.com.naat.news.application.NewController;
import mx.com.naat.news.domain.api.NewServicePort;
import mx.com.naat.news.domain.data.AddNewRequestDto;
import mx.com.naat.news.domain.data.NewDetailResponseDTO;
import mx.com.naat.news.domain.data.NewDto;
import mx.com.naat.news.domain.data.NewUpdateRequestDto;
import mx.com.naat.news.domain.data.UserDTO;
import mx.com.naat.news.domain.spi.NewPersistencePort;
import mx.com.naat.news.exception.BodyAlreadyExistsException;
import mx.com.naat.news.exception.HeadlineAlreadyExistsException;
import mx.com.naat.news.exception.NotFoundNewsIdException;
import mx.com.naat.news.exception.SummaryAlreadyExistsException;
import mx.com.naat.news.infrastructure.adapter.ServiceUpload;
import mx.com.naat.news.infrastructure.entity.New;

public class NewServiceImp implements NewServicePort {

	private NewPersistencePort newPersistencePort;

	public NewServiceImp(NewPersistencePort newPersistencePort) {
		this.newPersistencePort = newPersistencePort;
	}
	
	UUID usuario = NewController.getUserLog();

	@Override
	public List<NewDto> getNews(int page, int size, String sort) {
		Pageable paginacion = null;
		String[] orden = sort.split(",");

		switch (orden[1]) {
		case "desc":
			paginacion = PageRequest.of(page, size, Sort.by(orden[0]).descending());
			break;
		default:
			paginacion = PageRequest.of(page, size, Sort.by(orden[0]).ascending());
			break;
		}
		return newPersistencePort.getNews(paginacion);
	}

	@Override
	public NewDetailResponseDTO getNewById(UUID id) {
		Optional<New> news = newPersistencePort.getNewsById(id);

		if (news.isPresent()) {

			UserDTO user = getUserMS(news.get().getIdAuthor());
			NewDetailResponseDTO responseDTO = NewDetailResponseDTO.builder().id(news.get().getId())
					.creationDate(news.get().getCreationDate()).modificationDate(news.get().getModificationDate())
					.enabled(news.get().isEnabled()).idAuthor(user).headline(news.get().getHeadline())
					.summary(news.get().getSummary()).body(news.get().getBody()).image(news.get().getImage()).build();

			return responseDTO;
		}
		throw new NotFoundNewsIdException();
	}

	@Override
	public NewDetailResponseDTO addNew(AddNewRequestDto news) {
		New noticia = new New();
		
		noticia.setId(UUID.randomUUID());
		noticia.setCreationDate(LocalDateTime.now());
		noticia.setModificationDate(LocalDateTime.now());
		noticia.setEnabled(true);
		noticia.setIdAuthor(usuario);
		
		if (news.getHeadline() != null) {
			New newsHeadline = newPersistencePort.getHeadline(news.getHeadline());
			if (newsHeadline != null) {
				if (news.getHeadline().toLowerCase().equals(newsHeadline.getHeadline().toLowerCase())
						|| news.getHeadline().toUpperCase().equals(newsHeadline.getHeadline().toUpperCase())) {
					throw new HeadlineAlreadyExistsException();
				} 
			}
			noticia.setHeadline(news.getHeadline());
		}

		if (news.getBody() != null) {
			New newsBody = newPersistencePort.getBody(news.getBody());
			if (newsBody != null) {
				if (news.getBody().toLowerCase().equals(newsBody.getBody().toLowerCase())
						|| news.getBody().toUpperCase().equals(newsBody.getBody().toUpperCase())) {
					throw new BodyAlreadyExistsException();
				}
			}
			noticia.setBody(news.getBody());
		}

		if (news.getSummary() != null) {
			New newsSummary = newPersistencePort.getSummary(news.getSummary());
			if (newsSummary != null) {
				if (news.getSummary().toLowerCase().equals(newsSummary.getSummary().toLowerCase())
						|| news.getSummary().toUpperCase().equals(newsSummary.getSummary().toUpperCase())) {
					throw new SummaryAlreadyExistsException();
				}
			}
			noticia.setSummary(news.getSummary());
		}
		
		String ruta = ServiceUpload.save(news.getImage());

		String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(ruta).toUriString();

		noticia.setImage(fileUri);

		New noticia2 = newPersistencePort.addNew(noticia);

		UserDTO user = getUserMS(noticia2.getIdAuthor());
		NewDetailResponseDTO noticiaResponse = NewDetailResponseDTO.builder().id(noticia2.getId())
				.creationDate(noticia2.getCreationDate()).modificationDate(noticia2.getModificationDate())
				.enabled(noticia2.isEnabled()).idAuthor(user).headline(noticia2.getHeadline())
				.summary(noticia2.getSummary()).body(noticia2.getBody()).image(noticia2.getImage()).build();

		return noticiaResponse;
	}

	@Override
	public NewDetailResponseDTO updateNew(UUID id, NewUpdateRequestDto newUpdateDto) {
		Optional<New> newItem2 = newPersistencePort.getNewsById(id);
		
		if(newItem2.isPresent()) {
			New newItem = newPersistencePort.getNewsById(id).get();
			if(newUpdateDto.getHeadline() != null && !newUpdateDto.getHeadline().equals(newItem.getHeadline())
					&& newPersistencePort.getHeadline(newUpdateDto.getHeadline()) != null) {
				throw new HeadlineAlreadyExistsException();
			}
			if(newUpdateDto.getSummary() != null && !newUpdateDto.getSummary().equals(newItem.getSummary())
					&& newPersistencePort.getSummary(newUpdateDto.getSummary()) != null) {
				throw new SummaryAlreadyExistsException();
			}
			if(newUpdateDto.getBody() != null && !newUpdateDto.getBody().equals(newItem.getBody())
					&& newPersistencePort.getBody(newUpdateDto.getBody()) != null) {
				throw new BodyAlreadyExistsException();
			}
			if(newUpdateDto.getHeadline() != null) {
				newItem.setHeadline(newUpdateDto.getHeadline());
			}
			if(newUpdateDto.getSummary() != null) {
				newItem.setSummary(newUpdateDto.getSummary());
			}
			if(newUpdateDto.getBody() != null) {
				newItem.setBody(newUpdateDto.getBody());
			}
			if(newUpdateDto.getImage() != null) {
				ServiceUpload.delete(newItem.getImage());
				String ruta = ServiceUpload.save(newUpdateDto.getImage());
				String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(ruta).toUriString();
				newItem.setImage(fileUri);
			}
			newItem.setEnabled(newUpdateDto.isEnabled());
			newItem.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
			
			newPersistencePort.addNew(newItem);
			
			UserDTO user = getUserMS(newItem.getIdAuthor());
			NewDetailResponseDTO newDetailResponseDTO = NewDetailResponseDTO.builder().id(newItem.getId())
					.creationDate(newItem.getCreationDate()).modificationDate(newItem.getModificationDate())
					.enabled(newItem.isEnabled()).idAuthor(user).headline(newItem.getHeadline())
					.summary(newItem.getSummary()).body(newItem.getBody()).image(newItem.getImage()).build();
			return newDetailResponseDTO;
		}
		throw new NotFoundNewsIdException();
	}
	
	private UserDTO getUserMS(UUID id) {
		UserDTO result = null;

		try {
			String uri = "http://localhost:8080/users/" + id;
			RestTemplate restTemplate = new RestTemplate();
			result = restTemplate.getForObject(uri, UserDTO.class);
		} catch (ResourceAccessException e) {
			//throw new ConexionException();
			System.out.println(e.getMessage());
		}

		return result;
	}

}
