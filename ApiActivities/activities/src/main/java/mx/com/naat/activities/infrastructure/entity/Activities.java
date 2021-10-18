package mx.com.naat.activities.infrastructure.entity;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activities {
	@Id
	@Column(name = "ACTIVITY_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "CREATION_DATE", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)")
	private Date creationDate;
	
	@Column(name = "MODIFICATION_DATE", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)")
	private Date modificationDate;
	
	@Column(name = "ACTIVITY_ENABLED" ,columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean enabled;
	@NotNull
	@Column(name = "AUTHOR_ID", nullable = false)
	private UUID nameAuthor;
	@NotNull
	@Column(name = "ACTIVITY_KEY", nullable = false)
	private String key;
	@NotNull
	@Column(name = "ACTIVITY_NAME", nullable = false)
	private String name;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;


	public Activities(String key, String name, String description) {
		this.key = key;
		this.name = name;
		this.description = description;
	}
	@PrePersist
	public void preinsert()
	{
		/*creationDate = LocalDateTime.now();
		modificationDate = LocalDateTime.now();*/
	}
	@PreUpdate
	public void preupdate()
	{
		//modificationDate = LocalDateTime.now();
	}
}
