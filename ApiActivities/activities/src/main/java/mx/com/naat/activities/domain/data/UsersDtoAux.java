package mx.com.naat.activities.domain.data;

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
public class UsersDtoAux {
	private UUID id;
	private String name;
	private String lastName;
	private String email;
	private boolean enabled;
	private String role;
	private LocalDateTime vacationStart;
	private LocalDateTime vacationEnd;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	
}
