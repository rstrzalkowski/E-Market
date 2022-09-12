package pl.marketapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.marketapi.application.port.in.command.LoginCommand;
import pl.marketapi.application.port.in.usecase.LoginUseCase;
import pl.marketapi.application.port.out.LoadUserPort;
import pl.marketapi.common.exception.InvalidCredentialsException;
import pl.marketapi.common.exception.UserNotExistsException;
import pl.marketapi.common.security.JwtProvider;
import pl.marketapi.domain.User;

@RequiredArgsConstructor
@Service
public class LoginService implements LoginUseCase {

    private final LoadUserPort loadUserPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Override
    public String login(LoginCommand loginCommand) throws Exception {

        User user = loadUserPort.loadUserByUsername(loginCommand.username());

        if (user == null) {
            throw new UserNotExistsException();
        }

        if (!passwordEncoder.matches(loginCommand.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return jwtProvider.generateJWT(loginCommand.username());

    }
}
