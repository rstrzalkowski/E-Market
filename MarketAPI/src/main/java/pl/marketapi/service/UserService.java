package pl.marketapi.service;

import pl.marketapi.entity.Product;
import pl.marketapi.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getByUsername(String username) throws Exception;

    User updateUser(String username, User user) throws Exception;

    boolean register(User user);

    String authenticate(User user) throws Exception;


}
