package mx.com.naat.projects.infrastructure.exceptions;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonReturn {
	private long timeStamp;
	private int status;
	private String error;
	private String message;
	private String path;
	
}
