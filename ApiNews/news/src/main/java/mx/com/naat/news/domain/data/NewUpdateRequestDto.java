package mx.com.naat.news.domain.data;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUpdateRequestDto {
    private MultipartFile image;
    private String headline;
    private String summary;
    private String body;
    private boolean enabled;
}
