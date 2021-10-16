package mx.com.naat.activities.domain.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import mx.com.naat.activities.domain.data.ActivityDetail;
import mx.com.naat.activities.domain.spi.ActivityPersistancePort;
import mx.com.naat.activities.domain.spi.UserPersistencePort;
import mx.com.naat.activities.infrastructure.exceptions.ActivityNotFound;
import mx.com.naat.activities.infrastructure.exceptions.DescriptionExists;
import mx.com.naat.activities.infrastructure.exceptions.KeyExists;
import mx.com.naat.activities.infrastructure.exceptions.NameExists;
import mx.com.naat.activities.testdata.TestData;

@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {

    @Mock
    ActivityPersistancePort activityPersistancePort;
    @Mock
    UserPersistencePort userPersistencePort;
    @InjectMocks
    ActivityServiceImpl activityService;

    @Nested
    @Disabled
    class ActivityList
    {

    }
    @Nested
    class ActivityDetailTest
    {
        @Test
        void findSuccess()
        {
            when( activityPersistancePort.getActivityById(any())).thenReturn(Optional.of( TestData.ACTIVITY_1 ));
            when( userPersistencePort.findUserById( any() )).thenReturn( Optional.of(TestData.USERS_DTO));
            ActivityDetail returnedActivity = activityService.getActivityById( TestData.ACTIVITY_1.getId() );
            assertNotNull( returnedActivity );
            
        }
        @Test
        void activityNotFound()
        {
            when( activityPersistancePort.getActivityById( any() ) ).thenReturn( Optional.empty());
            Exception exception= assertThrows(ActivityNotFound.class, 
                                                ()->{activityService.getActivityById(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"));});
            assertEquals("NOT_FOUND", exception.getMessage());  
        }
        /* ¿como debe proceder el sistema si no encuentra los datos del autor? */
        @Test
        void authorNotFound()
        {
            when( activityPersistancePort.getActivityById(any())).thenReturn(Optional.of( TestData.ACTIVITY_1 ));
            when( userPersistencePort.findUserById( any() )).thenReturn( Optional.empty());
            assertTrue(true);
            Exception exception= assertThrows(RuntimeException.class, 
                                                ()->{activityService.getActivityById(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"));});
            assertEquals("USER_NOT_FOUND", exception.getMessage());  
        }
    }
    @Nested
    class ActivityCreate
    {
        @Test
        void createSuccess()
        {
            when( userPersistencePort.findUserById( any() )).thenReturn( Optional.of(TestData.USERS_DTO));
            when( activityPersistancePort.createActivity(any())).thenAnswer(answer -> answer.getArgument(0));

            ActivityDetail storedActivity = activityService.createActivity(
                                            UUID.fromString("00000000-0000-0000-0000-00000001"),
                                             TestData.ACTIVITY_RB);
            assertAll(
                    ()->{assertNotNull(storedActivity);},
                    ()->{ assertNotNull( storedActivity.getId()); } ,
                    ()->{ assertNotNull( storedActivity.getCreationDate()); } ,
                    ()->{ assertNotNull( storedActivity.getModificationDate()); } ,
                    ()->{ assertNotNull( storedActivity.getEnabled());},
                    ()->{ assertNotNull( storedActivity.getNameAuthor()); } ,
                    ()->{ assertNotNull( storedActivity.getIdAuthor()); } ,
                    ()->{ assertNotNull( storedActivity.getIdAuthor().getName()); } ,
                    ()->{ assertNotNull( storedActivity.getKey()); } ,
                    ()->{ assertNotNull( storedActivity.getName()); } ,
                    ()->{ assertNotNull( storedActivity.getDescription()); },
                    ()->{ assertEquals(true, storedActivity.getEnabled());},
                    ()->{ assertEquals(TestData.ACTIVITY_RB.getName(), storedActivity.getName());},
                    ()->{ assertEquals(TestData.ACTIVITY_RB.getKey(), storedActivity.getKey());},
                    ()->{ assertEquals(TestData.ACTIVITY_RB.getDescription(), storedActivity.getDescription());}
                    );
        }
        @Test
        void keyAlreadyExists()
        {
            when( activityPersistancePort.getActivityByKey(any())).thenReturn(Optional.of(TestData.ACTIVITY_2));
            Exception exception= assertThrows(KeyExists.class, 
                                                ()->{activityService.createActivity(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"),
                                                    TestData.ACTIVITY_RB);});
            assertEquals("KEY_ALREADY_EXISTS", exception.getMessage());
            
        }
        @Test
        void nameAlreadyExists()
        {
            when( activityPersistancePort.getActivityByName(any())).thenReturn(Optional.of(TestData.ACTIVITY_2));
            Exception exception= assertThrows(NameExists.class, 
                                                ()->{activityService.createActivity(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"),
                                                    TestData.ACTIVITY_RB);});
            assertEquals("NAME_ALREADY_EXISTS", exception.getMessage());
        }
        @Test
        void descriptionAlreadyExists()
        {
            when( activityPersistancePort.getActivityByDescription(any())).thenReturn(Optional.of(TestData.ACTIVITY_2));
            Exception exception= assertThrows(DescriptionExists.class, 
                                                ()->{activityService.createActivity(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"),
                                                    TestData.ACTIVITY_RB);});
            assertEquals("DESCRIPTION_ALREADY_EXISTS", exception.getMessage());
        }
    }
    @Nested
    class ActivityUpdate
    {

        @Test
        void updateSuccess()
        {
            when( activityPersistancePort.getActivityById(any())).thenReturn(Optional.of( TestData.ACTIVITY_1 ));
            when( userPersistencePort.findUserById( any() )).thenReturn( Optional.of(TestData.USERS_DTO));
            when( activityPersistancePort.putActivity(any())).thenAnswer(answer -> answer.getArgument(0));

            ActivityDetail storedActivity = activityService.putActivity(
                                            UUID.fromString("00000000-0000-0000-0000-00000001"),
                                             TestData.ACTIVITY_RB);
            assertAll(
                    ()->{assertNotNull(storedActivity);},
                    ()->{ assertNotNull( storedActivity.getId()); } ,
                    ()->{ assertNotNull( storedActivity.getCreationDate()); }, 
                    ()->{ assertNotNull( storedActivity.getModificationDate()); } ,
                    ()->{ assertNotEquals(storedActivity.getCreationDate(), storedActivity.getModificationDate());},
                    ()->{ assertNotNull( storedActivity.getEnabled());},
                    ()->{ assertNotNull( storedActivity.getNameAuthor()); } ,
                    ()->{ assertNotNull( storedActivity.getIdAuthor()); } ,
                    ()->{ assertNotNull( storedActivity.getIdAuthor().getName()); } ,
                    ()->{ assertNotNull( storedActivity.getKey()); } ,
                    ()->{ assertNotNull( storedActivity.getName()); } ,
                    ()->{ assertNotNull( storedActivity.getDescription()); },
                    ()->{ assertNotNull( storedActivity.getEnabled());},
                    ()->{ assertEquals(TestData.ACTIVITY_RB.getName(), storedActivity.getName());},
                    ()->{ assertEquals(TestData.ACTIVITY_RB.getKey(), storedActivity.getKey());},
                    ()->{ assertEquals(TestData.ACTIVITY_RB.getDescription(), storedActivity.getDescription());}
                    );
        }
        @Test
        void keyAlreadyExists()
        {
            when( activityPersistancePort.getActivityById(any())).thenReturn(Optional.of( TestData.ACTIVITY_1 ));
            when( activityPersistancePort.getActivityByKey(any())).thenReturn(Optional.of(TestData.ACTIVITY_2));
            Exception exception= assertThrows(KeyExists.class, 
                                                ()->{activityService.putActivity(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"),
                                                    TestData.ACTIVITY_RB);});
            assertEquals("KEY_ALREADY_EXISTS", exception.getMessage());
            
        }
        @Test
        void nameAlreadyExists()
        {
            when( activityPersistancePort.getActivityById(any())).thenReturn(Optional.of( TestData.ACTIVITY_1 ));
            when( activityPersistancePort.getActivityByName(any())).thenReturn(Optional.of(TestData.ACTIVITY_2));
            Exception exception= assertThrows(NameExists.class, 
                                                ()->{activityService.putActivity(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"),
                                                    TestData.ACTIVITY_RB);});
            assertEquals("NAME_ALREADY_EXISTS", exception.getMessage());
        }
        @Test
        void descriptionAlreadyExists()
        {
            when( activityPersistancePort.getActivityById(any())).thenReturn(Optional.of( TestData.ACTIVITY_1 ));
            when( activityPersistancePort.getActivityByDescription(any())).thenReturn(Optional.of(TestData.ACTIVITY_2));
            Exception exception= assertThrows(DescriptionExists.class, 
                                                ()->{activityService.putActivity(
                                                    UUID.fromString("00000000-0000-0000-0000-00000001"),
                                                    TestData.ACTIVITY_RB);});
            assertEquals("DESCRIPTION_ALREADY_EXISTS", exception.getMessage());
        }
        @Test
        void notFound()
        {
            
        }
    }
}