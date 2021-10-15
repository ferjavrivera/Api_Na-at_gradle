package mx.com.naat.activities.domain.data;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityRequestBody implements Serializable{

	private static final long serialVersionUID = 1L;
	@JsonIgnore
	private UUID id;
	@NotNull
	private String key;
	
	@NotNull
	private String name;

	@NotNull
	private String description;

	
	
}
