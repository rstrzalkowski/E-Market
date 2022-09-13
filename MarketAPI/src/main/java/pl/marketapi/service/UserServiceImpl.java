package pl.marketapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.marketapi.entity.LoginRequest;
import pl.marketapi.entity.RegisterRequest;
import pl.marketapi.entity.User;
import pl.marketapi.exception.InvalidCredentialsException;
import pl.marketapi.exception.UserAlreadyExistsException;
import pl.marketapi.exception.UserNotFoundException;
import pl.marketapi.repository.UserRepository;
import pl.marketapi.security.JwtProvider;

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
    public void register(RegisterRequest registerRequest) {
        Optional<User> sameUsernameUser = userRepository.findByUsername(registerRequest.getUsername().toLowerCase());
        Optional<User> sameEmailUser = userRepository.findByEmail(registerRequest.getEmail().toLowerCase());

        if (sameUsernameUser.isPresent() || sameEmailUser.isPresent()) {
            throw new UserAlreadyExistsException("User with this username or email already exists.");
        }

        //Mapping RegisterRequest object to User, which will be saved in database
        User user = new User(registerRequest);

        //Password has to be encoded before saving to database
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    @Override
    public String authenticate(LoginRequest loginRequest) {
        String username = loginRequest.getUsername().toLowerCase();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with this username doesn't exist.");
        }

        User dbUser = optionalUser.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), dbUser.getPassword())) {
            throw new InvalidCredentialsException("Invalid password.");
        }

        return jwtProvider.generateJWT(dbUser);
    }

    @Override
    public Page<User> getAll(Pageable page) {
        return userRepository.findAll(page);
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }

        throw new UserNotFoundException("User with this username doesn't exist");
    }

    @Override
    public User updateUser(String username, RegisterRequest registerRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User doesn't exist");
        }
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("This username is taken");
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Other account is registered on this email");
        }

        User existingUser = optionalUser.get();
        existingUser.setUsername(registerRequest.getUsername() == null? existingUser.getUsername() : registerRequest.getUsername());
        existingUser.setPassword(registerRequest.getPassword() == null? existingUser.getPassword() : passwordEncoder.encode(registerRequest.getPassword()));
        existingUser.setEmail(registerRequest.getEmail() == null? existingUser.getEmail() : registerRequest.getEmail());

        return userRepository.save(existingUser);
    }
}
