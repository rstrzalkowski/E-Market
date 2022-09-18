package pl.marketapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.marketapi.domain.dto.JWT;
import pl.marketapi.domain.dto.LoginRequest;
import pl.marketapi.domain.dto.RegisterRequest;
import pl.marketapi.domain.entity.User;

public interface UserService {

    User register(RegisterRequest registerRequest);

    JWT authenticate(LoginRequest loginRequest);

    Page<User> getAll(Pageable page);

    User getByEmail(String email);

    User updateUser(String username, RegisterRequest registerRequest);

    User lockUser(String email);

    User unlockUser(String email);
}
