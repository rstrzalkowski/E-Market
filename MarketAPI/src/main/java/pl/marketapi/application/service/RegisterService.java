package pl.marketapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.marketapi.application.port.in.command.RegisterCommand;
import pl.marketapi.application.port.in.usecase.RegisterUseCase;
import pl.marketapi.application.port.out.LoadUserPort;
import pl.marketapi.application.port.out.SaveUserPort;
import pl.marketapi.domain.User;

@Service
@RequiredArgsConstructor
public class RegisterService implements RegisterUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean register(RegisterCommand registerCommand) throws Exception {

        User user = new User(registerCommand.getUsername(), registerCommand.getPassword(), registerCommand.getEmail());

        if (loadUserPort.loadUserByUsername(user.getUsername()) != null) {
            throw new Exception("User with this username already exist.");
        }

        if (loadUserPort.loadUserByEmail(user.getEmail()) != null) {
            throw new Exception("User with this email already exist.");
        }

        //TODO better validation
        if (user.getPassword().length() < 8) {
            throw new Exception("Password is too weak");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        saveUserPort.saveUser(user);

        return true;

    }
}
