package pl.marketapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.marketapi.entity.LoginRequest;
import pl.marketapi.entity.RegisterRequest;
import pl.marketapi.entity.User;

public interface UserService {

    void register(RegisterRequest registerRequest);

    String authenticate(LoginRequest loginRequest);

    Page<User> getAll(Pageable page);

    User getByUsername(String username);

    User updateUser(String username, RegisterRequest registerRequest);
}
