package mx.com.naat.clients.domain.data;

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
public class ClientDtoAux {
	private UUID id;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private boolean enabled;
	private UsersAux idAuthor;
	private String key;
	private String name;
	private String description;
	
}
