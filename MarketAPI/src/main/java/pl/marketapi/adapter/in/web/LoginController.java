package pl.marketapi.adapter.in.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.marketapi.application.port.in.command.LoginCommand;
import pl.marketapi.application.port.in.usecase.LoginUseCase;
import pl.marketapi.common.exception.InvalidCredentialsException;
import pl.marketapi.common.exception.UserNotExistsException;

@RestController
public class LoginController {

    @Autowired
    private LoginUseCase loginUseCase;

    @GetMapping("/test")
    public String testAuth() {
        return "You are an authenticated user!";
    }

    @PostMapping("/auth")
    public String login(@RequestBody LoginCommand loginCommand) {
        try {
            return loginUseCase.login(loginCommand);
        } catch (UserNotExistsException e) {
            return "User doesn't exist";
        } catch (InvalidCredentialsException e) {
            return "Invalid password";
        } catch (Exception e) {
            return "HERE";
        }
    }

}
