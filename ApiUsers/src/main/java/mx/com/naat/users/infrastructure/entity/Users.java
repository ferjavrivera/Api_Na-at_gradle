package mx.com.naat.users.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
	@Id
	@Column(name = "USER_ID")
	private UUID id;
	
	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@Column(name = "MODIFICATION_DATE")
	private LocalDateTime modificationDate;
	
	@Column(name = "USER_ENABLED")
	private boolean enabled;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "USER_NAME")
	private String name;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "VACATION_START")
	private LocalDateTime vacationStart;
	
	@Column(name = "VACATION_ENDING")
	private LocalDateTime vacationEnd;
	
	@Column(name = "ROLE_ID")
	private String role;

	public Users(String email, String name, String lastName, LocalDateTime vacationStart, LocalDateTime vacationEnd, String role) {
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.vacationStart = vacationStart;
		this.vacationEnd = vacationEnd;
		this.role = role;
	}
	
}
