package mx.com.naat.news.domain.spi;

import org.springframework.data.domain.Pageable;

import mx.com.naat.news.domain.data.NewDto;
import mx.com.naat.news.infrastructure.entity.New;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewPersistencePort {
	
	List<NewDto> getNews(Pageable paginacion);
	
	Optional<New> getNewsById(UUID id);
	
	New addNew(New noticia);
	
	//New updateNew(UUID id, New noticia);
	
	New getHeadline(String headline);
	
	New getBody(String headline);
	
	New getSummary(String headline);
}
