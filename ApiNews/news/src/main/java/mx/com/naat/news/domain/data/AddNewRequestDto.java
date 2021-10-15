package mx.com.naat.news.domain.data;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddNewRequestDto {
	
	@NotNull
    private MultipartFile image;
	@NotNull
    private String headline;
	@NotNull
    private String summary;
	@NotNull
    private String body;
}