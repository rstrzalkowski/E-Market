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
    public void register(RegisterRequest request) {
        Optional<User> sameEmailUser = userRepository.findByEmail(request.getEmail().toLowerCase());

        if (sameEmailUser.isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }

        //Mapping RegisterRequest object to User, which will be saved in database
        User user = new User(request);

        //Password has to be encoded before saving to database
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    @Override
    public String authenticate(LoginRequest request) {
        String email = request.getEmail().toLowerCase();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with this email doesn't exist.");
        }

        User dbUser = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), dbUser.getPassword())) {
            throw new InvalidCredentialsException("Invalid password.");
        }

        return jwtProvider.generateJWT(dbUser);
    }

    @Override
    public Page<User> getAll(Pageable page) {
        return userRepository.findAll(page);
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email.toLowerCase());

        if (user.isPresent()) {
            return user.get();
        }

        throw new UserNotFoundException("User with this email doesn't exist");
    }

    @Override
    public User updateUser(String email, RegisterRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(email.toLowerCase());
        Optional<User> newEmailUser = userRepository.findByEmail(request.getEmail().toLowerCase());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User doesn't exist");
        }

        if (newEmailUser.isPresent()) {
            throw new UserAlreadyExistsException("Other account is registered on this email");
        }

        User existingUser = optionalUser.get();
        existingUser.setFirstName(request.getFirstName() == null? existingUser.getFirstName() : request.getFirstName());
        existingUser.setLastName(request.getLastName() == null? existingUser.getLastName() : request.getLastName());
        existingUser.setPassword(request.getPassword() == null? existingUser.getPassword() : passwordEncoder.encode(request.getPassword()));
        existingUser.setEmail(request.getEmail() == null? existingUser.getEmail() : request.getEmail());

        return userRepository.save(existingUser);
    }
}
