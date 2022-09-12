package pl.marketapi.application.port.in.usecase;

import pl.marketapi.application.port.in.command.LoginCommand;

public interface LoginUseCase {
    String login(LoginCommand loginCommand) throws Exception;
}
