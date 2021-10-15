package mx.com.naat.clients.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonReturn {
	private long timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	
}
