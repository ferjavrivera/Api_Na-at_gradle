package mx.com.naat.projects.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mx.com.naat.projects.infrastructure.entity.Projects;

@Repository
public interface ProjectsRepository extends PagingAndSortingRepository<Projects, UUID>{
	Projects findByKeyIgnoreCase(String email);
	Projects findByNameIgnoreCase(String name);
	Projects findByDescriptionIgnoreCase(String description);
	Page<Projects> findAllByIdClient(UUID id, Pageable pageable);
}
