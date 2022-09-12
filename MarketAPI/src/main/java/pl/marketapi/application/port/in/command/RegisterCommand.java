package pl.marketapi.application.port.in.command;

import lombok.Data;
import pl.marketapi.common.SelfValidating;

@Data
public class RegisterCommand extends SelfValidating<RegisterCommand> {
    private final String username;
    private final String email;
    private final String password;

    public RegisterCommand(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        //validateSelf();
    }
}
