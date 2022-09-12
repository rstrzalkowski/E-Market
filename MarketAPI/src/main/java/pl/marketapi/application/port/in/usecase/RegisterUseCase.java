package pl.marketapi.application.port.in.usecase;

import pl.marketapi.application.port.in.command.RegisterCommand;

public interface RegisterUseCase {

    boolean register(RegisterCommand registerCommand) throws Exception;
}
