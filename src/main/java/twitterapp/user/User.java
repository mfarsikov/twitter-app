package twitterapp.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    private String username;
    private String password;
    private UUID resetPasswordToken;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Optional<UUID> getResetPasswordToken() {
        return Optional.ofNullable(resetPasswordToken);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
