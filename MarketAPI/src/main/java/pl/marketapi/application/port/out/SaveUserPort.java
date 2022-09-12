package pl.marketapi.application.port.out;

import pl.marketapi.adapter.out.persistence.entity.UserEntity;
import pl.marketapi.domain.User;

public interface SaveUserPort {
    UserEntity saveUser(User user);
}
