package mx.com.naat.activities.domain.data;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDto {
	private UUID id;
	private String key;
	private String name;
	private String description;
	
	
	
}
