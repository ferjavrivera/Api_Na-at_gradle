package mx.com.naat.activities.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import mx.com.naat.activities.domain.data.ActivityDetail;
import mx.com.naat.activities.domain.data.ActivityDto;
import mx.com.naat.activities.domain.data.ActivityRequestBody;
import mx.com.naat.activities.infrastructure.entity.Activities;

@Mapper
public interface ActivityMapper {
	ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

	ActivityDto activitiesToActivityDto(Activities activities);
	List<ActivityDto> activitiesListToActivityDtoList(List<Activities> usersList); 
	@Mapping(target = "idAuthor", ignore = true)
	ActivityDetail activitiesToActivityDetail(Activities activities);
	
	
	Activities activityDetailToActivities(ActivityDetail activityDetail);
	ActivityDetail activityRequestBodyToActivityDetail(ActivityRequestBody activityRB);

}
