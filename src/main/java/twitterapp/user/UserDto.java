package twitterapp.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import twitterapp.validation.PasswordMatch;
import twitterapp.validation.PasswordMatchable;

@Data
@PasswordMatch
public class UserDto implements PasswordMatchable {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String matchingPassword;
}