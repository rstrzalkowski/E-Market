package pl.marketapi.adapter.out.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.marketapi.adapter.out.persistence.entity.UserEntity;
import pl.marketapi.adapter.out.persistence.mapper.UserMapper;
import pl.marketapi.adapter.out.persistence.repository.UserRepository;
import pl.marketapi.application.port.out.LoadUserPort;
import pl.marketapi.application.port.out.SaveUserPort;
import pl.marketapi.domain.User;

@Component
@AllArgsConstructor
class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public User loadUserByUsername(String username) throws Exception {
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        return userMapper.mapToUser(user);
    }

    @Override
    public User loadUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        return userMapper.mapToUser(user);
    }

    @Override
    public UserEntity saveUser(User user) {
        return userRepository.save(new UserEntity(
                null,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                true));
    }
}
