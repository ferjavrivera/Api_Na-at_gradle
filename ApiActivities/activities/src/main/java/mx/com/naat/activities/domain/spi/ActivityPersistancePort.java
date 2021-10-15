package mx.com.naat.activities.domain.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import mx.com.naat.activities.domain.data.ActivityDetail;
import mx.com.naat.activities.domain.data.ActivityDto;

public interface ActivityPersistancePort {
	List<ActivityDto> getAllActivities(int page, int size, String[] sort);
	
	Optional<ActivityDetail> getActivityById(UUID id);
	Optional<ActivityDetail> getActivityByKey(String key);
	Optional<ActivityDetail> getActivityByName(String name);
	Optional<ActivityDetail> getActivityByDescription(String description);


	ActivityDetail putActivity( ActivityDetail activityDetail );
	long countRegisters();
	ActivityDetail createActivity( ActivityDetail activityDetail );
}
