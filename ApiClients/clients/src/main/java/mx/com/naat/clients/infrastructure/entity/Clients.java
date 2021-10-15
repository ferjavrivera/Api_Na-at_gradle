package mx.com.naat.clients.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Clients {
	@Id
	@Column(name = "CLIENT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@Column(name = "MODIFICATION_DATE")
	private LocalDateTime modificationDate;
	
	@Column(name = "CLIENT_ENABLED")
	private boolean enabled;

	@Column(name = "AUTHOR_ID")
	private UUID idAuthor;
	
	@Column(name = "CLIENT_KEY")
	private String key;
	
	@Column(name = "CLIENT_NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;


	public Clients(String key, String name, String description) {
		this.key = key;
		this.name = name;
		this.description = description;
	}
	
}
