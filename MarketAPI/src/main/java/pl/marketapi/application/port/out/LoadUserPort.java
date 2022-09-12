package pl.marketapi.application.port.out;

import pl.marketapi.domain.User;

public interface LoadUserPort {

    User loadUserByUsername(String username) throws Exception;

    User loadUserByEmail(String email);

}
