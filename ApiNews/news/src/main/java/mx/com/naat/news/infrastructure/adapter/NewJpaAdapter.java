package mx.com.naat.news.infrastructure.adapter;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.naat.news.domain.data.NewDto;
import mx.com.naat.news.domain.spi.NewPersistencePort;
import mx.com.naat.news.infrastructure.entity.New;
import mx.com.naat.news.infrastructure.mapper.NewMapper;
import mx.com.naat.news.infrastructure.repository.NewRepository;

@Service
public class NewJpaAdapter implements NewPersistencePort {

	@Autowired
	private NewRepository newRepository;
	@Override
	public List<NewDto> getNews(Pageable paginacion) {
		Iterable<New> newsList = newRepository.findAll(paginacion);
		return NewMapper.INSTANCE.newListToNewDtoList(newsList);
	}

	@Override
	public Optional<New> getNewsById(UUID id) {
		return newRepository.findById(id);
	}
	
	@Override
	public New getHeadline(String headline) {
		return newRepository.findByHeadlineIgnoreCase(headline);
	}
	
	@Override
	public New getBody(String headline) {
		return newRepository.findByBodyIgnoreCase(headline);
	}
	
	@Override
	public New getSummary(String headline) {
		return newRepository.findBySummaryIgnoreCase(headline);
	}

	@Override
	public New addNew(New noticia) {
		return newRepository.save(noticia);
	}

}
