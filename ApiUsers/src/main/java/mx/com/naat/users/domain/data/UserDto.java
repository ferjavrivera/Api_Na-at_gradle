package mx.com.naat.users.domain.data;

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
public class UserDto {
	private UUID id;
	private LocalDateTime creationDate;
	private boolean enabled;
	private String email;
	private String name;
	private String lastName;
	private String role;
	
	
	
}
