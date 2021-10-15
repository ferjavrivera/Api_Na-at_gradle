package mx.com.naat.activities.infrastructure.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import mx.com.naat.activities.domain.data.ActivityDetail;
import mx.com.naat.activities.domain.data.ActivityDto;
import mx.com.naat.activities.domain.spi.ActivityPersistancePort;
import mx.com.naat.activities.infrastructure.entity.Activities;
import mx.com.naat.activities.infrastructure.mapper.ActivityMapper;
import mx.com.naat.activities.infrastructure.repository.ActivityRepository;

@Service
public class ActivityJpaAdapter implements ActivityPersistancePort{

	@Autowired
	private ActivityRepository activityRepository;
	
	@Override
	public List<ActivityDto> getAllActivities(int page, int size, String[] sortMethod) {
		List<Order> orders = new ArrayList<Order>();
		if(sortMethod.length > 1) {
			if(sortMethod[1].equals("desc")) {
				orders.add(new Order(Sort.Direction.DESC, sortMethod[0]));
			}else {
				orders.add(new Order(Sort.Direction.ASC, sortMethod[0]));
			}
		}else {
			orders.add(new Order(Sort.Direction.ASC,sortMethod[0]));
		}
		
		Pageable paginSort = PageRequest.of(page, size, Sort.by(orders));
		
		
		return ActivityMapper.INSTANCE.activitiesListToActivityDtoList(activityRepository.findAll(paginSort).getContent());
		
	}

	@Override
	public Optional<ActivityDetail> getActivityById(UUID id) {
		Optional<Activities> activities = activityRepository.findById(id);
		if( activities.isEmpty() )
			return Optional.empty();
		Activities activity = activities.orElseThrow();
		return Optional.of( ActivityMapper.INSTANCE.activitiesToActivityDetail( activity ));
	}

	@Override
	public ActivityDetail putActivity(ActivityDetail activityDetail) {
		
		return createActivity(activityDetail);
	}

	@Override
	public ActivityDetail createActivity(ActivityDetail activityDetail) 
	{
		Activities storedActivity = activityRepository.save(ActivityMapper.INSTANCE.activityDetailToActivities(activityDetail));
		return ActivityMapper.INSTANCE.activitiesToActivityDetail( storedActivity );
	
		
	}

	@Override
	public long countRegisters() {
		return activityRepository.count();
	}

	@Override
	public Optional<ActivityDetail> getActivityByKey(String key) {
		Optional<Activities> activity = activityRepository.findByKeyIgnoreCase(key);
		if( activity.isEmpty())
			return Optional.empty();
		return Optional.of(ActivityMapper.INSTANCE.activitiesToActivityDetail(activity.orElseThrow()));
	}

	@Override
	public Optional<ActivityDetail> getActivityByName(String name) {
		Optional<Activities> activity = activityRepository.findByNameIgnoreCase(name);
		if( activity.isEmpty())
			return Optional.empty();
		return Optional.of(ActivityMapper.INSTANCE.activitiesToActivityDetail(activity.orElseThrow()));

	}

	@Override
	public Optional<ActivityDetail> getActivityByDescription(String description) {
		Optional<Activities> activity = activityRepository.findByDescriptionIgnoreCase(description);
		if( activity.isEmpty())
			return Optional.empty();
		return Optional.of(ActivityMapper.INSTANCE.activitiesToActivityDetail(activity.orElseThrow()));
	}
}
