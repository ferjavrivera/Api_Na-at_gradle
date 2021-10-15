package mx.com.naat.news.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mx.com.naat.news.infrastructure.entity.New;

@Repository
public interface NewRepository extends PagingAndSortingRepository <New,UUID> {

	New findByHeadlineIgnoreCase(String headline);
	
	New findByBodyIgnoreCase(String body);
	
	New findBySummaryIgnoreCase(String summary);
}
