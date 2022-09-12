package pl.marketapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marketapi.entity.User;
import pl.marketapi.service.UserService;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{username}")
    public User getByUsername(@PathVariable String username) {
        try {
            return userService.getByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/users/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        try {
            return userService.updateUser(username, user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/auth")
    public String login(@RequestBody User user) {
        try {
            return userService.authenticate(user);
        } catch (Exception e) {
            return "Authentication failed";
        }
    }


}
