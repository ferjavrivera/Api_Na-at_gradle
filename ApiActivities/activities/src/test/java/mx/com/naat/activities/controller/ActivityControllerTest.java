package mx.com.naat.activities.controller;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import mx.com.naat.activities.domain.api.ActivityServicePort;
import mx.com.naat.activities.domain.data.ActivityDetail;
import mx.com.naat.activities.domain.data.ActivityDto;
import mx.com.naat.activities.domain.data.AuthorDto;
import mx.com.naat.activities.infrastructure.exceptions.ActivityManagementExceptions;
import mx.com.naat.activities.infrastructure.exceptions.ActivityNotFound;
import mx.com.naat.activities.infrastructure.exceptions.DescriptionExists;
import mx.com.naat.activities.infrastructure.exceptions.KeyExists;
import mx.com.naat.activities.infrastructure.exceptions.NameExists;
import mx.com.naat.activities.testdata.TestData;

@WebMvcTest( {ActivityController.class,ActivityManagementExceptions.class})
class ActivityControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ActivityServicePort activityServicePort;
    @Nested
    class ActivityList
    {
        @Test
        @DisplayName("Se verifica la respuesta de la API de la forma correcta")
        public void testActivityList() throws Exception
        {
            //Creamos una lista que usará el mock
            List<ActivityDto> listActivities = new ArrayList<>();
            for (int j = 1; j < 12; j++) {
                ActivityDto a =  ActivityDto.builder()
                .id( UUID.randomUUID() ).name("act" + j).key("ACT_" + j)
                .description("descAct" + j).build();
                listActivities.add(a);
            }
            
            // Comportamiento del mock
            when(activityServicePort.getAllActivities(anyInt(),anyInt(), any()))
            .thenReturn(listActivities);
            when(activityServicePort.countRegisters())
            .thenReturn((long)listActivities.size());

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/activities"))
                    .andExpect(status().isOk())
                    .andExpect(header().string("Content-Type","application/json"))
                    .andExpect(header().string("Total-Elements",""+listActivities.size()))
                    .andExpect(jsonPath("$",hasSize(listActivities.size())));
                    
            verify(activityServicePort).getAllActivities(anyInt(), anyInt(), any());
        }
    }
    
    @Nested
    class activityDetail
    {
        
        @Test
        public void activityDetailSuccess() throws Exception
        {
            ActivityDetail activity = TestData.ACTIVITY_1;
            when(activityServicePort.getActivityById(UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb14")))
                .thenReturn( activity );
            mockMvc.perform(MockMvcRequestBuilders.get("/activities/fe3f7dbf-7515-45c2-9d31-f8a7658cdb14"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type","application/json"))
                .andExpect(jsonPath("$['id']", is("fe3f7dbf-7515-45c2-9d31-f8a7658cdb14")))
                .andExpect(jsonPath("$['creationDate']").hasJsonPath())
                .andExpect(jsonPath("$['modificationDate']").hasJsonPath())
                .andExpect(jsonPath("$['enabled']").hasJsonPath())
                .andExpect(jsonPath("$['enabled']", is(true)))
                .andExpect(jsonPath("$['idAuthor']").isMap())
                .andExpect(jsonPath("$['idAuthor']['name']").exists())
                .andExpect(jsonPath("$['key']").exists())
                .andExpect(jsonPath("$['key']", is("ACTIVITY_X_KEY")))
                .andExpect(jsonPath("$['name']").exists())
                .andExpect(jsonPath("$['name']", is("Nombre de la actividad")))
                .andExpect(jsonPath("$['description']").exists())
                .andExpect(jsonPath("$['description']", is("Descripción de la actividad")));

            
        }
        @Test
        public void activityDetailNotFound() throws Exception
        {
            when(activityServicePort.getActivityById(UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb14")))
                    .thenThrow( new ActivityNotFound("NOT_FOUND"));
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/activities/fe3f7dbf-7515-45c2-9d31-f8a7658cdb14"))
                    .andExpect(status().isNotFound())
                    .andExpect(header().string("Content-Type","application/json"))
                    .andExpect(jsonPath("$['status']", is(404)))
                    .andExpect(jsonPath("$['error']", is("Not Found")))
                    .andExpect(jsonPath("$['message']", is("NOT_FOUND")))
                    .andExpect(jsonPath("$['path']", is("/activities")))
                    .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
    }

    @Nested
    class activityCreate
    {
        String activityJsonString;
        @BeforeEach
        void setUp()
        {
            activityJsonString = TestData.ACTIVITY_JSON_STRING;
        }
        @Test
        void success() throws Exception
        {
            ActivityDetail activity = TestData.ACTIVITY_1;
            when(activityServicePort.createActivity( ArgumentMatchers.any(), ArgumentMatchers.any() ))
                .thenReturn( activity );

            mockMvc.perform(MockMvcRequestBuilders.post("/activities")
                .contentType(APPLICATION_JSON)
                .content(activityJsonString))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type","application/json"))
                .andExpect(jsonPath("$['id']", is("fe3f7dbf-7515-45c2-9d31-f8a7658cdb14")))
                .andExpect(jsonPath("$['creationDate']").exists())
                .andExpect(jsonPath("$['modificationDate']").exists())
                .andExpect(jsonPath("$['enabled']").exists())
                .andExpect(jsonPath("$['enabled']", is(true)))
                .andExpect(jsonPath("$['idAuthor']").exists())
                .andExpect(jsonPath("$['idAuthor']").isMap())
                .andExpect(jsonPath("$['idAuthor']['name']").exists())
                .andExpect(jsonPath("$['key']").exists())
                .andExpect(jsonPath("$['key']", is("ACTIVITY_X_KEY")))
                .andExpect(jsonPath("$['name']").exists())
                .andExpect(jsonPath("$['name']", is("Nombre de la actividad")))
                .andExpect(jsonPath("$['description']").exists())
                .andExpect(jsonPath("$['description']", is("Descripción de la actividad")));
            
        }
        @Test
        void nameAlreadyExists() throws Exception
        {
            when(activityServicePort.createActivity( ArgumentMatchers.any(), ArgumentMatchers.any() )).thenThrow(new NameExists("NAME_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .post("/activities")
            .contentType(APPLICATION_JSON)
            .content(activityJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("NAME_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/activities")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
        @Test
        void descriptionAlreadyExists() throws Exception
        {
            when(activityServicePort.createActivity( ArgumentMatchers.any(),ArgumentMatchers.any() )).thenThrow(new DescriptionExists("DESCRIPTION_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .post("/activities")
            .contentType(APPLICATION_JSON)
            .content(activityJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("DESCRIPTION_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/activities")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
        @Test
        void keyAlreadyExists() throws Exception
        {
            when(activityServicePort.createActivity( ArgumentMatchers.any(), ArgumentMatchers.any() )).thenThrow(new KeyExists("KEY_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .post("/activities")
            .contentType(APPLICATION_JSON)
            .content(activityJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("KEY_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/activities")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
    }


    @Nested
    class activityUpdate
    {
        String activityJsonString;
        @BeforeEach
        void setUp()
        {
            activityJsonString = TestData.ACTIVITY_JSON_STRING;
        }
        @Test
        void success() throws Exception
        {
            

            AuthorDto author = new AuthorDto("Test");
            ActivityDetail activity = ActivityDetail.builder()
                                        .id(UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb14"))
                                        .creationDate(LocalDateTime.now())
                                        .modificationDate(LocalDateTime.now())
                                        .enabled(true)
                                        .key("ACTIVITY_X_KEY")
                                        .name("Nombre de la actividad")
                                        .description("Descripción de la actividad")
                                        .idAuthor( author )
                                        .build();
            when(activityServicePort.putActivity( ArgumentMatchers.any(), ArgumentMatchers.any() ))
                .thenReturn( activity );

            mockMvc.perform(MockMvcRequestBuilders.put("/activities/fe3f7dbf-7515-45c2-9d31-f8a7658cdb14")
                .contentType(APPLICATION_JSON)
                .content(activityJsonString))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type","application/json"))
                .andExpect(jsonPath("$['id']", is("fe3f7dbf-7515-45c2-9d31-f8a7658cdb14")))
                .andExpect(jsonPath("$['creationDate']").exists())
                .andExpect(jsonPath("$['modificationDate']").exists())
                .andExpect(jsonPath("$['enabled']").exists())
                .andExpect(jsonPath("$['enabled']", is(true)))
                .andExpect(jsonPath("$['idAuthor']").exists())
                .andExpect(jsonPath("$['idAuthor']").isMap())
                .andExpect(jsonPath("$['idAuthor']['name']").exists())
                .andExpect(jsonPath("$['key']").exists())
                .andExpect(jsonPath("$['key']", is("ACTIVITY_X_KEY")))
                .andExpect(jsonPath("$['name']").exists())
                .andExpect(jsonPath("$['name']", is("Nombre de la actividad")))
                .andExpect(jsonPath("$['description']").exists())
                .andExpect(jsonPath("$['description']", is("Descripción de la actividad")));
            
        }
        @Test
        void nameAlreadyExists() throws Exception
        {
            when(activityServicePort.putActivity( ArgumentMatchers.any(), ArgumentMatchers.any() )).thenThrow(new NameExists("NAME_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .put("/activities/fe3f7dbf-7515-45c2-9d31-f8a7658cdb14")
            .contentType(APPLICATION_JSON)
            .content(activityJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("NAME_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/activities")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
        @Test
        void descriptionAlreadyExists() throws Exception
        {
            when(activityServicePort.putActivity( ArgumentMatchers.any(),ArgumentMatchers.any() )).thenThrow(new DescriptionExists("DESCRIPTION_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .put("/activities/fe3f7dbf-7515-45c2-9d31-f8a7658cdb14")
            .contentType(APPLICATION_JSON)
            .content(activityJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("DESCRIPTION_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/activities")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
        @Test
        void keyAlreadyExists() throws Exception
        {
            when(activityServicePort.putActivity( ArgumentMatchers.any(), ArgumentMatchers.any() )).thenThrow(new KeyExists("KEY_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .put("/activities/fe3f7dbf-7515-45c2-9d31-f8a7658cdb14")
            .contentType(APPLICATION_JSON)
            .content(activityJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("KEY_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/activities")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
    }
}