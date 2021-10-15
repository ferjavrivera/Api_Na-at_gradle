package mx.com.naat.activities.domain.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import mx.com.naat.activities.domain.api.ActivityServicePort;
import mx.com.naat.activities.domain.data.ActivityDetail;
import mx.com.naat.activities.domain.data.ActivityDto;
import mx.com.naat.activities.domain.data.ActivityRequestBody;
import mx.com.naat.activities.domain.data.AuthorDto;
import mx.com.naat.activities.domain.data.UsersDtoAux;
import mx.com.naat.activities.domain.spi.ActivityPersistancePort;
import mx.com.naat.activities.domain.spi.UserPersistencePort;
import mx.com.naat.activities.infrastructure.exceptions.ActivityNotFound;
import mx.com.naat.activities.infrastructure.exceptions.DescriptionExists;
import mx.com.naat.activities.infrastructure.exceptions.KeyExists;
import mx.com.naat.activities.infrastructure.exceptions.NameExists;
import mx.com.naat.activities.infrastructure.mapper.ActivityMapper;

public class ActivityServiceImpl implements ActivityServicePort{

	private ActivityPersistancePort activityPersistancePort;
	private UserPersistencePort 
	userPersistencePort;
	
	/* CONSTRUCTOR */
	public ActivityServiceImpl(ActivityPersistancePort activityPersistancePort, UserPersistencePort userPersistencePort) {
		this.activityPersistancePort = activityPersistancePort;
		this.userPersistencePort = userPersistencePort;
	}

	
	/*
	*  Este metodo retorna una lista de ActivityDetail (DTO)
	 */
	@Override
	public List<ActivityDto> getAllActivities(int page, int size, String[] sort) {
		return activityPersistancePort.getAllActivities(page, size, sort);
	}

	@Override
	public ActivityDetail getActivityById(UUID id) {
		Optional<ActivityDetail> opAct =activityPersistancePort.getActivityById(id);
		if( opAct.isEmpty() )
			throw new ActivityNotFound("NOT_FOUND");
		ActivityDetail ad = opAct.orElseThrow();
		
		ad.setIdAuthor( findUserById( ad.getNameAuthor() ) );
		return opAct.orElseThrow();
	}

	
	@Override
	public ActivityDetail putActivity(UUID user, ActivityRequestBody activityRequestBody) {
		ActivityDetail ad = ActivityMapper.INSTANCE.activityRequestBodyToActivityDetail(activityRequestBody);
		/* Verificamos que no falten datos */
		validateObligatoryFields(ad);
		Optional<ActivityDetail> activityDB = activityPersistancePort.getActivityById(ad.getId());
		if( activityDB.isEmpty() )
			throw new ActivityNotFound("NOT_FOUND");
		ActivityDetail activityDetailDb = activityDB.orElseThrow();
		activityDetailDb.setKey( ad.getKey() );
		activityDetailDb.setName( ad.getName());
		activityDetailDb.setDescription( ad.getDescription() );
		/* Si hay una Key, name, o description igual generara la excepcion */
		validateRestrictions(activityDetailDb);
		activityDetailDb.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
		AuthorDto author = findUserById( user );
		ActivityDetail storedActivity = activityPersistancePort.putActivity(activityDetailDb);
		storedActivity.setIdAuthor( author );
		return storedActivity;
	}
	@Override
	public ActivityDetail createActivity(UUID user, ActivityRequestBody activityRequestBody) {
		ActivityDetail ad = ActivityMapper.INSTANCE.activityRequestBodyToActivityDetail(activityRequestBody);
		/* Verificamos que no falten datos */
		validateObligatoryFields(ad);
		/* Si hay una Key, name, o description igual generara la excepcion */
		validateRestrictions(ad);
		/*  LLenamos el objeto con los datos necesarios para un insert completo */
		ad.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
		ad.setModificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
		ad.setEnabled( true );
		ad.setNameAuthor( user );
		AuthorDto author = findUserById( user );
		ActivityDetail storedActivity = activityPersistancePort.createActivity(ad);
		storedActivity.setIdAuthor( author );
		return storedActivity;
	}

	@Override
	public long countRegisters() {
		return activityPersistancePort.countRegisters();
	}

	private void validateRestrictions(ActivityDetail activityDetail)
	{
		Optional<ActivityDetail> activityDB = activityPersistancePort.getActivityByKey(activityDetail.getKey());
		if ( activityDB.isPresent() )
			if( activityDB.get().getId() == null || activityDetail.getId() != activityDB.get().getId() )
				throw new KeyExists("KEY_ALREADY_EXISTS");
		activityDB = activityPersistancePort.getActivityByName(activityDetail.getName());
		if ( activityDB.isPresent() )
			if( activityDB.get().getId() == null || activityDetail.getId() != activityDB.get().getId() )
				throw new NameExists("NAME_ALREADY_EXISTS");
		activityDB = activityPersistancePort.getActivityByDescription( activityDetail.getDescription() );
		if ( activityDB.isPresent() )
			if( activityDB.get().getId() == null || activityDetail.getId() != activityDB.get().getId() )
				throw new DescriptionExists("DESCRIPTION_ALREADY_EXISTS");
	}
	private void validateObligatoryFields(ActivityDetail activityDetail)
	{
		if( activityDetail.getKey() == null)
			throw new RuntimeException("KEY_FIELD_OBLIGATORY");
		if( activityDetail.getName() == null)
			throw new RuntimeException("KEY_FIELD_OBLIGATORY");
		if( activityDetail.getDescription() == null)
			throw new RuntimeException("KEY_FIELD_OBLIGATORY");
	}
	private AuthorDto findUserById( UUID idAuthor )
	{
		Optional<UsersDtoAux> userDetail = userPersistencePort.findUserById( idAuthor );
		if(userDetail.isEmpty())
			throw new RuntimeException("USER_NOT_FOUND");
		UsersDtoAux userDtoAux = userDetail.orElseThrow();

		AuthorDto authorDto = AuthorDto.builder().name(userDtoAux.getName()).build();
		return authorDto;
	}

}
