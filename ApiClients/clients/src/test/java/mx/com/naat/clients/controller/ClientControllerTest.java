package mx.com.naat.clients.controller;

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

import mx.com.naat.clients.domain.api.ClientServicePort;
import mx.com.naat.clients.domain.data.ClientDto;
import mx.com.naat.clients.domain.data.ClientDtoAux;
import mx.com.naat.clients.domain.data.UsersAux;
import mx.com.naat.clients.infrastructure.entity.Clients;
import mx.com.naat.clients.infrastructure.exceptions.ClientNotFound;
import mx.com.naat.clients.infrastructure.exceptions.DescriptionExists;
import mx.com.naat.clients.infrastructure.exceptions.KeyExists;
import mx.com.naat.clients.infrastructure.exceptions.NameExists;

import mx.testdata.TestData;


@WebMvcTest( ClientController.class)
public class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ClientServicePort clientServicePort;
    
    @Nested
    class ClientList
    {
        @Test
        @DisplayName("Se verifica la respuesta de la API de la forma correcta")
        public void testClientList() throws Exception
        {
            //Creamos una lista que usará el mock
            List<ClientDto> listClients = new ArrayList<>();
            for (int j = 1; j < 12; j++) {
            	ClientDto a =  ClientDto.builder()
                .id( UUID.randomUUID() ).name("client" + j).key("CLIENT_" + j)
                .description("descClient" + j).build();
            	listClients.add(a);
            }
            
            // Comportamiento del mock
            when(clientServicePort.getAllClients(anyInt(),anyInt(), any()))
            .thenReturn(listClients);
            when(clientServicePort.countRegisters())
            .thenReturn((long)listClients.size());

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/clients"))
                    .andExpect(status().isOk())
                    .andExpect(header().string("Content-Type","application/json"))
                    .andExpect(header().string("Total-Elements",""+listClients.size()))
                    .andExpect(jsonPath("$",hasSize(listClients.size())));
                    
            verify(clientServicePort).getAllClients(anyInt(), anyInt(), any());
        }
    }
    
    @Nested
    class ClientDetail
    {
        @Test
        public void clientDetailSuccess() throws Exception
        {
            ClientDtoAux client = TestData.CLIENT_1;
            when(clientServicePort.getClientById(UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb19")))
                .thenReturn( client );
            mockMvc.perform(MockMvcRequestBuilders.get("/clients/fe3f7dbf-7515-45c2-9d31-f8a7658cdb19"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type","application/json"))
                .andExpect(jsonPath("$['id']", is("fe3f7dbf-7515-45c2-9d31-f8a7658cdb19")))
                .andExpect(jsonPath("$['creationDate']").hasJsonPath())
                .andExpect(jsonPath("$['modificationDate']").hasJsonPath())
                .andExpect(jsonPath("$['enabled']").hasJsonPath())
                .andExpect(jsonPath("$['enabled']", is(true)))
                .andExpect(jsonPath("$['idAuthor']").isMap())
                .andExpect(jsonPath("$['idAuthor']['name']").exists())
                .andExpect(jsonPath("$['key']").exists())
                .andExpect(jsonPath("$['key']", is("BODEGA34")))
                .andExpect(jsonPath("$['name']").exists())
                .andExpect(jsonPath("$['name']", is("Bodega34")))
                .andExpect(jsonPath("$['description']").exists())
                .andExpect(jsonPath("$['description']", is("smnyl24")));

            
        }
        @Test
        public void clientDetailNotFound() throws Exception
        {
            when(clientServicePort.getClientById(UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb00")))
                    .thenThrow( new ClientNotFound("NOT_FOUND"));
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/clients/fe3f7dbf-7515-45c2-9d31-f8a7658cdb00"))
                    .andExpect(status().isNotFound())
                    .andExpect(header().string("Content-Type","application/json"))
                    .andExpect(jsonPath("$['status']", is(404)))
                    .andExpect(jsonPath("$['error']", is("Not Found")))
                    .andExpect(jsonPath("$['message']", is("NOT_FOUND")))
                    .andExpect(jsonPath("$['path']", is("/clients")))
                    .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
    }
    
    
    @Nested
    class ClientCreate
    {
        String clientJsonString;
        @BeforeEach
        void setUp()
        {
        	clientJsonString = TestData.CLIENT_JSON_STRING;
        }
        @Test
        void success() throws Exception
        {
            ClientDtoAux client = TestData.CLIENT_1;
            when(clientServicePort.createClient(ArgumentMatchers.any()))
                .thenReturn( client );

            mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                .contentType(APPLICATION_JSON)
                .content(clientJsonString))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type","application/json"))
                .andExpect(jsonPath("$['id']", is("fe3f7dbf-7515-45c2-9d31-f8a7658cdb19")))
                .andExpect(jsonPath("$['creationDate']").exists())
                .andExpect(jsonPath("$['modificationDate']").exists())
                .andExpect(jsonPath("$['enabled']").exists())
                .andExpect(jsonPath("$['enabled']", is(true)))
                .andExpect(jsonPath("$['idAuthor']").isMap())
                .andExpect(jsonPath("$['idAuthor']['name']").exists())
                .andExpect(jsonPath("$['key']").exists())
                .andExpect(jsonPath("$['key']", is("BODEGA34")))
                .andExpect(jsonPath("$['name']").exists())
                .andExpect(jsonPath("$['name']", is("Bodega34")))
                .andExpect(jsonPath("$['description']").exists())
                .andExpect(jsonPath("$['description']", is("smnyl24")));
            
        }
        @Test
        void nameAlreadyExists() throws Exception
        {
            when(clientServicePort.createClient( ArgumentMatchers.any())).thenThrow(new NameExists("NAME_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .post("/clients")
            .contentType(APPLICATION_JSON)
            .content(clientJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("NAME_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/clients")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
        @Test
        void descriptionAlreadyExists() throws Exception
        {
            when(clientServicePort.createClient( ArgumentMatchers.any() )).thenThrow(new DescriptionExists("DESCRIPTION_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .post("/clients")
            .contentType(APPLICATION_JSON)
            .content(clientJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("DESCRIPTION_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/clients")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
        @Test
        void keyAlreadyExists() throws Exception
        {
            when(clientServicePort.createClient( ArgumentMatchers.any() )).thenThrow(new KeyExists("KEY_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .post("/clients")
            .contentType(APPLICATION_JSON)
            .content(clientJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("KEY_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/clients")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
    }
    
    @Nested
    class ClientUpdate
    {
        String clientJsonString;
        @BeforeEach
        void setUp()
        {
            clientJsonString = TestData.CLIENT_JSON_STRING;
        }
        @Test
        void success() throws Exception
        {
            

        	UsersAux author = new UsersAux("Test");
            ClientDtoAux client = ClientDtoAux.builder()
                                        .id(UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb19"))
                                        .creationDate(LocalDateTime.now())
                                        .modificationDate(LocalDateTime.now())
                                        .enabled(true)
                                        .key("BODEGA34")
                                        .name("Bodega34")
                                        .description("smnyl24")
                                        .idAuthor(author)
                                        .build();
            when(clientServicePort.putClient( ArgumentMatchers.any(), ArgumentMatchers.any() ))
                .thenReturn( client );

            mockMvc.perform(MockMvcRequestBuilders.put("/clients/fe3f7dbf-7515-45c2-9d31-f8a7658cdb19")
                .contentType(APPLICATION_JSON)
                .content(clientJsonString))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type","application/json"))
                .andExpect(jsonPath("$['id']", is("fe3f7dbf-7515-45c2-9d31-f8a7658cdb19")))
                .andExpect(jsonPath("$['creationDate']").exists())
                .andExpect(jsonPath("$['modificationDate']").exists())
                .andExpect(jsonPath("$['enabled']").exists())
                .andExpect(jsonPath("$['enabled']", is(true)))
                .andExpect(jsonPath("$['idAuthor']").exists())
                .andExpect(jsonPath("$['idAuthor']").isMap())
                .andExpect(jsonPath("$['idAuthor']['name']").exists())
                .andExpect(jsonPath("$['key']").exists())
                .andExpect(jsonPath("$['key']", is("BODEGA34")))
                .andExpect(jsonPath("$['name']").exists())
                .andExpect(jsonPath("$['name']", is("Bodega34")))
                .andExpect(jsonPath("$['description']").exists())
                .andExpect(jsonPath("$['description']", is("smnyl24")));
            
        }
        @Test
        void nameAlreadyExists() throws Exception
        {
            when(clientServicePort.createClient( ArgumentMatchers.any())).thenThrow(new NameExists("NAME_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .post("/clients")
            .contentType(APPLICATION_JSON)
            .content(clientJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("NAME_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/clients")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
        @Test
        void descriptionAlreadyExists() throws Exception
        {
            when(clientServicePort.createClient( ArgumentMatchers.any() )).thenThrow(new DescriptionExists("DESCRIPTION_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .post("/clients")
            .contentType(APPLICATION_JSON)
            .content(clientJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("DESCRIPTION_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/clients")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
        @Test
        void keyAlreadyExists() throws Exception
        {
            when(clientServicePort.createClient( ArgumentMatchers.any() )).thenThrow(new KeyExists("KEY_ALREADY_EXISTS"));

            mockMvc.perform(MockMvcRequestBuilders
            .post("/clients")
            .contentType(APPLICATION_JSON)
            .content(clientJsonString))
                .andExpect(jsonPath("$['status']", is(400)))
                .andExpect(jsonPath("$['error']", is("Bad Request")))
                .andExpect(jsonPath("$['message']", is("KEY_ALREADY_EXISTS")))
                .andExpect(jsonPath("$['path']", is("/clients")))
                .andExpect(jsonPath("$['timestamp']", isA(Long.class)));
        }
    }
}
