package mx.com.naat.news.domain.api;

import java.util.List;
import java.util.UUID;

import mx.com.naat.news.domain.data.AddNewRequestDto;
import mx.com.naat.news.domain.data.NewDetailResponseDTO;
import mx.com.naat.news.domain.data.NewDto;
import mx.com.naat.news.domain.data.NewUpdateRequestDto;

public interface NewServicePort {
	
	List<NewDto> getNews(int page, int size, String sort);
	
	NewDetailResponseDTO getNewById(UUID id);
	
	NewDetailResponseDTO addNew(AddNewRequestDto news);
	
	NewDetailResponseDTO updateNew(UUID id, NewUpdateRequestDto news);

}
