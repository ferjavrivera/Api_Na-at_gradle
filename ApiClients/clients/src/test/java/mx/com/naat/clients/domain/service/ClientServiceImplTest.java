package mx.com.naat.clients.domain.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.com.naat.clients.domain.data.ClientDtoAux;
import mx.com.naat.clients.domain.spi.ClientPersistancePort;
import mx.com.naat.clients.infrastructure.exceptions.ClientNotFound;
import mx.com.naat.clients.infrastructure.exceptions.DescriptionExists;
import mx.com.naat.clients.infrastructure.exceptions.KeyExists;
import mx.com.naat.clients.infrastructure.exceptions.NameExists;

import mx.testdata.TestData;


@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {
	@Mock
    ClientPersistancePort clientPersistancePort;
	
    //@Mock
    //UserPersistencePort userPersistencePort;
	
    @InjectMocks
    ClientServiceImpl clientService;
    
    @Nested
    @Disabled
    class ClientList
    {

    }
    
    @Nested
    class ClientDetailTest
    {
        @Test
        void findSuccess()
        {
            when( clientPersistancePort.getClientById(any())).thenReturn(Optional.of( TestData.CLIENT_4 ));

            ClientDtoAux returnedClient = clientService.getClientById( TestData.CLIENT_4.getId() );
            assertNotNull( returnedClient );
            
        }
        @Test
        void activityNotFound()
        {
            when( clientPersistancePort.getClientById( any() ) ).thenReturn( Optional.empty());
            Exception exception= assertThrows(ClientNotFound.class, 
                                                ()->{clientService.getClientById(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"));});
            assertEquals("not found", exception.getMessage());  
        }
        /* ¿como debe proceder el sistema si no encuentra los datos del autor? */
        
        /*
        @Test
        void authorNotFound()
        {
            when( clientPersistancePort.getClientById(any())).thenReturn(Optional.of( TestData.CLIENT_4 ));
            //when( userPersistencePort.findUserById( any() )).thenReturn( Optional.empty());
            assertTrue(true);
            Exception exception= assertThrows(RuntimeException.class, 
                                                ()->{clientService.getClientById(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"));});
            assertEquals("not found", exception.getMessage());  
        }
        */
    }
    
    @Nested
    class ClientCreate
    {
        @Test
        void createSuccess()
        {
            //when( userPersistencePort.findUserById( any() )).thenReturn( Optional.of(TestData.USERS_DTO));
            when( clientPersistancePort.createClient(any())).thenAnswer(answer -> answer.getArgument(0));

            ClientDtoAux storedClient = clientService.createClient(
                                             TestData.CLIENT_RB);
            assertAll(
                    ()->{assertNotNull(storedClient);},
                    ()->{ assertNotNull( TestData.CLIENT_RB.getId()); } ,
                    ()->{ assertNotNull( storedClient.getCreationDate()); } ,
                    ()->{ assertNotNull( storedClient.getModificationDate()); } ,
                    ()->{ assertNotNull( storedClient.isEnabled());},
                    //()->{ assertNotNull( storedActivity.getNameAuthor()); } ,
                    ()->{ assertNotNull( storedClient.getIdAuthor()); } ,
                    ()->{ assertNotNull( storedClient.getIdAuthor().getName()); } ,
                    ()->{ assertNotNull( storedClient.getKey()); } ,
                    ()->{ assertNotNull( storedClient.getName()); } ,
                    ()->{ assertNotNull( storedClient.getDescription()); },
                    ()->{ assertEquals(true, storedClient.isEnabled());},
                    ()->{ assertEquals(TestData.CLIENT_RB.getName(), storedClient.getName());},
                    ()->{ assertEquals(TestData.CLIENT_RB.getKey(), storedClient.getKey());},
                    ()->{ assertEquals(TestData.CLIENT_RB.getDescription(), storedClient.getDescription());}
                    );
        }
        @Test
        void keyAlreadyExists()
        {
            when( clientPersistancePort.getKey(any())).thenReturn(TestData.CLIENT_4);
            Exception exception= assertThrows(KeyExists.class, 
                                                ()->{clientService.createClient(
                                                    TestData.CLIENT_RB);});
            assertEquals("key already exists", exception.getMessage());
            
        }
        @Test
        void nameAlreadyExists()
        {
            when( clientPersistancePort.getName(any())).thenReturn(TestData.CLIENT_4);
            Exception exception= assertThrows(NameExists.class, 
                                                ()->{clientService.createClient(
                                                    TestData.CLIENT_RB);});
            assertEquals("name already exists", exception.getMessage());
        }
        @Test
        void descriptionAlreadyExists()
        {
            when( clientPersistancePort.getDescription(any())).thenReturn(TestData.CLIENT_4);
            Exception exception= assertThrows(DescriptionExists.class, 
                                                ()->{clientService.createClient(
                                                    TestData.CLIENT_RB);});
            assertEquals("description already exists", exception.getMessage());
        }
    }
    
    
    @Nested
    class ClientUpdate
    {

        @Test
        void updateSuccess()
        {
            when( clientPersistancePort.getClientById(any())).thenReturn(Optional.of( TestData.CLIENT_4 ));
            //when( userPersistencePort.findUserById( any() )).thenReturn( Optional.of(TestData.USERS_DTO));
            when( clientPersistancePort.putClient(any())).thenAnswer(answer -> answer.getArgument(0));

            ClientDtoAux storedClient = clientService.putClient(
            		UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb17"),
                    TestData.CLIENT_RB);
            assertAll(
                    ()->{assertNotNull(storedClient);},
                    ()->{ assertNotNull( TestData.CLIENT_RB.getId()); } ,
                    ()->{ assertNotEquals( storedClient.getCreationDate(), storedClient.getModificationDate());},
                    ()->{ assertNotNull( storedClient.getModificationDate()); } ,
                    ()->{ assertNotNull( storedClient.isEnabled());},
                    //()->{ assertNotNull( storedActivity.getNameAuthor()); } ,
                    ()->{ assertNotNull( storedClient.getIdAuthor()); } ,
                    ()->{ assertNotNull( storedClient.getIdAuthor().getName()); } ,
                    ()->{ assertNotNull( storedClient.getKey()); } ,
                    ()->{ assertNotNull( storedClient.getName()); } ,
                    ()->{ assertNotNull( storedClient.getDescription()); },
                    ()->{ assertEquals(true, storedClient.isEnabled());},
                    ()->{ assertEquals(TestData.CLIENT_RB.getName(), storedClient.getName());},
                    ()->{ assertEquals(TestData.CLIENT_RB.getKey(), storedClient.getKey());},
                    ()->{ assertEquals(TestData.CLIENT_RB.getDescription(), storedClient.getDescription());}
                    );
        }
        @Test
        void keyAlreadyExists()
        {   
        	 when( clientPersistancePort.getClientById(any())).thenReturn(Optional.of( TestData.CLIENT_5));
        	 when( clientPersistancePort.getKey(any())).thenReturn(TestData.CLIENT_4);
             Exception exception= assertThrows(KeyExists.class, 
                                                 ()->{clientService.putClient(
                                                 		UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb17"),
                                               		TestData.CLIENT_RB);});
            assertEquals("key already exists", exception.getMessage());   
        }
        
        @Test
        void nameAlreadyExists()
        {
            when( clientPersistancePort.getClientById(any())).thenReturn(Optional.of( TestData.CLIENT_5));
            when( clientPersistancePort.getName(any())).thenReturn(TestData.CLIENT_4);
            Exception exception= assertThrows(NameExists.class, 
                                                ()->{clientService.putClient(
                                                		UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb17"),
                                                    TestData.CLIENT_RB);});
            assertEquals("name already exists", exception.getMessage());
        }
        @Test
        void descriptionAlreadyExists()
        {
        	when( clientPersistancePort.getClientById(any())).thenReturn(Optional.of( TestData.CLIENT_5));
        	when( clientPersistancePort.getDescription(any())).thenReturn(TestData.CLIENT_4);
             Exception exception= assertThrows(DescriptionExists.class, 
                                                 ()->{clientService.putClient(
                                                 		UUID.fromString("fe3f7dbf-7515-45c2-9d31-f8a7658cdb17"),
                                                        TestData.CLIENT_RB);});
             assertEquals("description already exists", exception.getMessage());
        }
        @Test
        void notFound()
        {
            
        }
    }
    
    
}
