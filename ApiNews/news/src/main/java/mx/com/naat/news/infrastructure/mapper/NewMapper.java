package mx.com.naat.news.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.com.naat.news.domain.data.NewDto;
import mx.com.naat.news.infrastructure.entity.New;

@Mapper
public interface NewMapper {
	 
	NewMapper INSTANCE = Mappers.getMapper(NewMapper.class);
	
	//NewDto map(New value);

    List<NewDto> newListToNewDtoList(Iterable<New> newList);
	
}
