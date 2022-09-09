package pl.marketapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String sayHello() {
        return "Hello";
    }
    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        return userService.add(user);
    }

    @PostMapping("/auth")
    public String login(@RequestBody User user) {
        return userService.authenticate(user);
    }


}
