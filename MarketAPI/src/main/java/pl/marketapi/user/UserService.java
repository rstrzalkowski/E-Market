package pl.marketapi.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public String authenticate(User user) {
        String username = user.getUsername();
        User dbUser = userRepository.findByUsername(username);

        if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 60000))
                    .signWith(SignatureAlgorithm.HS512, System.getenv("SECRET"))
                    .compact();
        } else return "Invalid username or password";

    }
}
