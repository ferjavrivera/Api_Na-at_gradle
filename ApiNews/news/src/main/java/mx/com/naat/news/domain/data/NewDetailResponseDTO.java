package mx.com.naat.news.domain.data;

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
public class NewDetailResponseDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
   
	private UUID id;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private boolean enabled;
	private UserDTO idAuthor;
	private String headline;
	private String summary;
	private String body; 
	private String image;
}
