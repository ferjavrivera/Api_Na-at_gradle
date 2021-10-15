package mx.com.naat.activities.infrastructure.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.client.RestTemplate;

import mx.com.naat.activities.domain.data.UsersDtoAux;
import mx.com.naat.activities.domain.spi.UserPersistencePort;

public class UserAdapter implements UserPersistencePort{

    @Override
    public Optional<UsersDtoAux> findUserById(UUID id)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/users/";
        UsersDtoAux user = restTemplate.getForObject(url + id.toString(), UsersDtoAux.class);
        
        if(user == null)
            return Optional.empty();
        return Optional.of(user);
    }

    
}
