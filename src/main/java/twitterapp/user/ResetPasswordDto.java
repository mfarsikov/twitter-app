package twitterapp.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import twitterapp.validation.PasswordMatch;
import twitterapp.validation.PasswordMatchable;

@Data
@NoArgsConstructor
@PasswordMatch
public class ResetPasswordDto implements PasswordMatchable {
    @NotEmpty
    private String password;
    @NotEmpty
    private String matchingPassword;
    @NotEmpty
    private String token;

    public ResetPasswordDto(String token) {
        this.token = token;
    }
}
