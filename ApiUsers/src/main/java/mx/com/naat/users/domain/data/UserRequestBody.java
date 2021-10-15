package mx.com.naat.users.domain.data;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestBody implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String email;
	
	@NotNull
	private String name;

	@NotNull
	private String lastName;
	
	private LocalDateTime vacationStart;
	
	private LocalDateTime vacationEnd;
	
	@NotNull
	private String role;
	
	
}
