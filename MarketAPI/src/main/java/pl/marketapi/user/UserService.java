package pl.marketapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.marketapi.security.JwtProvider;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean add(User user) {
        //TODO password restrictions

        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
            //TODO exception, user with this username already exists.
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return true;
    }

    //TODO custom exceptions
    public String authenticate(User user) throws Exception {
        String username = user.getUsername();
        User dbUser = userRepository.findByUsername(username);

        if (dbUser == null || !passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new Exception("User doesn't exist or invalid password");
        }

        return jwtProvider.generateJWT(dbUser);
    }
}
