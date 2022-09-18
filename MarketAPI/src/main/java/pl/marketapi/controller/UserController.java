package pl.marketapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.marketapi.domain.dto.JWT;
import pl.marketapi.domain.dto.LoginRequest;
import pl.marketapi.domain.dto.RegisterRequest;
import pl.marketapi.domain.entity.User;
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
    public User register(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public JWT login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public List<User> getAll(Pageable page) {
        return userService.getAll(page).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{email}")
    public User getByUsername(@PathVariable String email) {
        try {
            return userService.getByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/users/{email}")
    public User updateUser(@PathVariable String email, @RequestBody RegisterRequest registerRequest) {
        return userService.updateUser(email, registerRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/users/lock/{email}")
    public User lockUser(@PathVariable String email) {
        return userService.lockUser(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/users/unlock/{email}")
    public User unlockUser(@PathVariable String email) {
        return userService.unlockUser(email);
    }

}
