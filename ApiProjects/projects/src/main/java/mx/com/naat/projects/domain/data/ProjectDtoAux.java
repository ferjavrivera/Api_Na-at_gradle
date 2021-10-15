package mx.com.naat.projects.domain.data;

import java.io.Serializable;
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
public class ProjectDtoAux implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private boolean enabled;
	private UsersAux idAuthor;
	private String key;
	private String name;
	private String description;
	private ClientsAux idClient;
}
