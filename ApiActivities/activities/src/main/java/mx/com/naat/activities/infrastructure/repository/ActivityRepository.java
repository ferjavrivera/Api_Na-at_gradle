package mx.com.naat.activities.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import mx.com.naat.activities.infrastructure.entity.Activities;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends PagingAndSortingRepository<Activities, UUID>{
	Optional<Activities> findByKeyIgnoreCase(String email);
	Optional<Activities> findByNameIgnoreCase(String name);
	Optional<Activities> findByDescriptionIgnoreCase(String description);
}
