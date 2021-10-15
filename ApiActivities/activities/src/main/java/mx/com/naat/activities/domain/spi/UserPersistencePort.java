package mx.com.naat.activities.domain.spi;

import java.util.Optional;
import java.util.UUID;

import mx.com.naat.activities.domain.data.UsersDtoAux;

public interface UserPersistencePort {
    Optional<UsersDtoAux> findUserById(UUID id);
}
