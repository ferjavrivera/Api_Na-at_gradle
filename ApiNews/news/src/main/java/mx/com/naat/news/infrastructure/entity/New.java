package mx.com.naat.news.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name="NEWS")
public class New{
	
	@Id
	@Column(name="NEWS_ID")
	private UUID id;
	
	@Column(name="CREATION_DATE")
	private LocalDateTime creationDate;
	
	@Column(name="MODIFICATION_DATE")
	private LocalDateTime modificationDate;
	
	@Column(name="NEWS_ENABLED")
	private boolean enabled;
	
	@Column(name="AUTHOR_ID")
	private UUID idAuthor;
	
	@Column(name="HEADLINE")
    private String headline;
	
	@Column(name="SUMMARY")
	private String summary;
	
	@Column(name="NEWS_BODY")
	private String body;
	
	@Column(name="IMAGE")
	private String image;


}
