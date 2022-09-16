package pl.marketapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.marketapi.entity.LoginRequest;
import pl.marketapi.entity.RegisterRequest;
import pl.marketapi.entity.User;
import pl.marketapi.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }

    @GetMapping("/users")
    public List<User> getAll(Pageable page) {
        return userService.getAll(page).toList();
    }

    @GetMapping("/users/{email}")
    public User getByUsername(@PathVariable String email) {
        try {
            return userService.getByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/users/{username}")
    public User updateUser(@PathVariable String username, @RequestBody RegisterRequest registerRequest) {
        return userService.updateUser(username, registerRequest);
    }
}
