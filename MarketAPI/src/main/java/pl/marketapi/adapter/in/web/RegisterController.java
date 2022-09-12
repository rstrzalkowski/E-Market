package pl.marketapi.adapter.in.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.marketapi.application.port.in.command.RegisterCommand;
import pl.marketapi.application.port.in.usecase.RegisterUseCase;

@RestController
public class RegisterController {

    @Autowired
    private RegisterUseCase registerUseCase;

    @PostMapping("/register")
    public String register(@RequestBody RegisterCommand registerCommand) {
        try {
            registerUseCase.register(registerCommand);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "Registered succesfully!";
    }

}
