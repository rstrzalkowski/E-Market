package pl.marketapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.marketapi.entity.User;
import pl.marketapi.repository.UserRepository;
import pl.marketapi.security.JwtProvider;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getByUsername(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }

        throw new Exception("User " + username + " doesn`t exist");
    }

    @Override
    public User updateUser(String username, User user) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new Exception("User doesn't exist");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("This username is taken");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Other account is registered on this email");
        }
        if (user.getPassword() != null && user.getPassword().length() < 8) {
            throw new Exception("Password needs to be at least 8 characters long");
        }

        User existingUser = optionalUser.get();
        existingUser.setUsername(user.getUsername() == null? existingUser.getUsername() : user.getUsername());
        existingUser.setPassword(user.getPassword() == null? existingUser.getPassword() : passwordEncoder.encode(user.getPassword()));
        existingUser.setEmail(user.getEmail() == null? existingUser.getEmail() : user.getEmail());


        return userRepository.save(existingUser);
    }

    @Override
    public boolean register(User user) {
        //TODO password restrictions

        if (userRepository.findByUsername(user.getUsername()).isPresent() || userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false;
            //TODO exception, user with this username already exists.
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return true;
    }

    //TODO custom exceptions
    @Override
    public String authenticate(User user) throws Exception {
        String username = user.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {
            throw new Exception("User doesn't exist");
        }

        User dbUser = optionalUser.get();

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new Exception("Invalid password");
        }

        return jwtProvider.generateJWT(dbUser);
    }
}
