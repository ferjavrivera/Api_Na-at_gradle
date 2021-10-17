package mx.testdata;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import mx.com.naat.clients.domain.data.ClientDtoAux;
import mx.com.naat.clients.domain.data.ClientRequestBody;
import mx.com.naat.clients.domain.data.UsersAux;
import mx.com.naat.clients.domain.data.UsersDtoAux;
import mx.com.naat.clients.infrastructure.entity.Clients;

public class TestData {
	
	   public static String CLIENT_JSON_STRING = "{\"key\": \"ACTIVITY_X_KEY\"," +
			    "  \"name\":\"Nombre de la actividad\"," +
			    "  \"description\": \"Descripción de la actividad\"}";
	   			public static UsersAux AUTHOR = new UsersAux("Test");
	   			
			    public static UsersDtoAux USERS_DTO = new UsersDtoAux()
			                                            .builder()
			                                            .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
			                                            .name("Test")
			                                            .lastName("Example")
			                                            .role("ADMINISTRATOR")
			                                            .enabled(true)
			                                            .email("test@example.com")
			                                            .build();
			    
			    public static ClientDtoAux CLIENT_1 = ClientDtoAux.builder()
			                                .id( UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb19"))
			                                .creationDate(LocalDateTime.now())
			                                .modificationDate(LocalDateTime.now())
			                                .enabled(true)
			                                .idAuthor(AUTHOR)
			                                .key("BODEGA34")
			                                .name("Bodega34")
			                                .description("smnyl24")
			                                .build();
			    
			    public static ClientRequestBody CLIENT_RB = ClientRequestBody.builder()
			                                                    .id( UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb20"))
			                                                    .key("SMNYL_B")
			                                                    .name("SMNYL")
			                                                    .description("smnyl")
			                                                    .build();
			    
			    public static ClientDtoAux CLIENT_2 = ClientDtoAux.builder()
                        .id( UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb18"))
                        .creationDate(LocalDateTime.now())
                        .modificationDate(LocalDateTime.now())
                        .enabled(true)
                        .idAuthor(AUTHOR)
                        .key("HSBC_C")
                        .name("HSBC")
                        .description("hsbc")
                        .build();
			    
			    public static ClientDtoAux CLIENT_3 = ClientDtoAux.builder()
                        .id( UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb19"))
                        .creationDate(LocalDateTime.now())
                        .modificationDate(LocalDateTime.now())
                        .enabled(true)
                        .idAuthor(AUTHOR)
                        .key("KARUM_A")
                        .name("Karum")
                        .description("Karum")
                        .build();
			    
			    public static Clients CLIENT_4 = Clients.builder()
                        .id( UUID.fromString("c4b8d932-64d6-45a2-a94e-693cd006e2f9"))
                        .creationDate(LocalDateTime.now())
                        .modificationDate(LocalDateTime.now())
                        .enabled(true)
                        .idAuthor(UUID.fromString("00000000-0000-0000-0000-000000000010"))
                        .key("SMNYL_B")
                        .name("SMNYL")
                        .description("smnyl")
                        .build();
			    
			    public static Clients CLIENT_5 = Clients.builder()
                        .id( UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb20"))
                        .creationDate(LocalDateTime.now())
                        .modificationDate(LocalDateTime.now())
                        .enabled(true)
                        .idAuthor(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                        .key("SMNYL_C")
                        .name("SMNYL C")
                        .description("smnyl C")
                        .build();

			    public static List<ClientDtoAux> ACTIVITIES = Arrays.asList(CLIENT_1, CLIENT_2, CLIENT_3);
}
