package mx.com.naat.activities.testdata;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import mx.com.naat.activities.domain.data.ActivityDetail;
import mx.com.naat.activities.domain.data.ActivityRequestBody;
import mx.com.naat.activities.domain.data.AuthorDto;
import mx.com.naat.activities.domain.data.UsersDtoAux;


public class TestData {
    public static String ACTIVITY_JSON_STRING = "{\"key\": \"ACTIVITY_X_KEY\"," +
    "  \"name\":\"Nombre de la actividad\"," +
    "  \"description\": \"Descripción de la actividad\"}";
    public static AuthorDto AUTHOR = new AuthorDto("Test");
    public static UsersDtoAux USERS_DTO = new UsersDtoAux()
                                            .builder()
                                            .id(UUID.fromString("00000000-0000-0000-0000-00000001"))
                                            .name("Test")
                                            .lastName("Example")
                                            .role("ADMINISTRATOR")
                                            .enabled(true)
                                            .email("test@example.com")
                                            .build();
    public static ActivityDetail ACTIVITY_1 = ActivityDetail.builder()
                                .id( UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb14"))
                                .creationDate(LocalDateTime.now())
                                .modificationDate(LocalDateTime.now())
                                .enabled(true)
                                .key("ACTIVITY_X_KEY")
                                .name("Nombre de la actividad")
                                .description("Descripción de la actividad")
                                .idAuthor( AUTHOR )
                                .nameAuthor(UUID.fromString("00000000-0000-0000-0000-00000001"))
                                .build();
    public static ActivityRequestBody ACTIVITY_RB = ActivityRequestBody.builder()
                                                    .id( UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb14"))
                                                    .key("ACTIVITY_X_KEY")
                                                    .name("Nombre de la actividad")
                                                    .description("Descripción de la actividad")
                                                    .build();
    public static ActivityDetail ACTIVITY_2 = ActivityDetail.builder()
                                .id( UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb15"))
                                .creationDate(LocalDateTime.now())
                                .modificationDate(LocalDateTime.now())
                                .enabled(true)
                                .key("ACTIVITY_2_KEY")
                                .name("Nombre de la actividad2")
                                .description("Descripción de la actividad2")
                                .idAuthor( AUTHOR )
                                .build();
    
    public static ActivityDetail ACTIVITY_3 = ActivityDetail.builder()
                                .id( UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb15"))
                                .creationDate(LocalDateTime.now())
                                .modificationDate(LocalDateTime.now())
                                .enabled(true)
                                .key("ACTIVITY_3_KEY")
                                .name("Nombre de la actividad3")
                                .description("Descripción de la actividad3")
                                .idAuthor( AUTHOR )
                                .build();

    public static List<ActivityDetail> ACTIVITIES = Arrays.asList( ACTIVITY_1, ACTIVITY_2, ACTIVITY_3 );
    
}
