package mx.com.naat.news.domain.data;


import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewDto{
	
	private UUID id;
	private String image;
	private LocalDateTime creationDate;
	private String headline;
	private String summary;
	private String body; 
}
