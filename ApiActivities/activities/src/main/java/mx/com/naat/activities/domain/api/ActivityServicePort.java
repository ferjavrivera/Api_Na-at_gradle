package mx.com.naat.activities.domain.api;

import java.util.List;
import java.util.UUID;

import mx.com.naat.activities.domain.data.ActivityDetail;
import mx.com.naat.activities.domain.data.ActivityDto;
import mx.com.naat.activities.domain.data.ActivityRequestBody;

public interface ActivityServicePort {
	List<ActivityDto> getAllActivities(int page, int size, String[] sort);
	ActivityDetail getActivityById(UUID id);
	ActivityDetail putActivity( UUID user, ActivityRequestBody activityRequestBody);
	long countRegisters();
	ActivityDetail createActivity( UUID user, ActivityRequestBody activityRequestBody );
}
