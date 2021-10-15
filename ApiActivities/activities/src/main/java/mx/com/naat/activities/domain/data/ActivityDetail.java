package mx.com.naat.activities.domain.data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ActivityDetail implements Serializable {
	private UUID id;
	
	private LocalDateTime creationDate;
	
	private LocalDateTime modificationDate;

	private boolean enabled;

    @JsonIgnore
    private UUID nameAuthor;

	private AuthorDto idAuthor;
	
	private String key;
	
	private String name;
	
	private String description;

	public boolean getEnabled()
	{
		return enabled;
	}
}

