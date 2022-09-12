package pl.marketapi.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;
import pl.marketapi.adapter.out.persistence.entity.UserEntity;
import pl.marketapi.domain.User;

@Component
public class UserMapper {

    public User mapToUser(UserEntity userEntity) {
        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getEmail());
    }

}
