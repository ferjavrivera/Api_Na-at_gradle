package mx.com.naat.projects.domain.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequestBody implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String key;
	
	@NotNull
	private String name;

	@NotNull
	private String description;

	
	
}
