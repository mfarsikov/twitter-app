package twitterapp.user;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userReporitory;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userReporitory.findByUsername(username)
                             .orElseThrow(() -> new UsernameNotFoundException(username));

    }

    public void addUser(String name, String password) {
        userReporitory.save(new User(name, passwordEncoder.encode(password)));
    }

    public UUID resetPasswordToken(User user) {
        UUID restorePasswordToken = UUID.randomUUID();
        user.setResetPasswordToken(restorePasswordToken);
        userReporitory.save(user);
        return restorePasswordToken;
    }

    public Optional<User> findUserByName(String username) {
        return userReporitory.findByUsername(username);
    }

    public void resetPassword(UUID resetPasswordToken, String newPassword) {
        userReporitory.findByResetPasswordToken(resetPasswordToken)
                      .ifPresent(user -> resetPassword(user, newPassword));
    }

    private void resetPassword(User user, String newPassword) {
        user.setResetPasswordToken(null);
        user.setPassword(passwordEncoder.encode(newPassword));
        userReporitory.save(user);
    }
}
