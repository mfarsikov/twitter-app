package twitterapp.user;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("user", new UserDto());

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") @Valid UserDto userDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        userService.addUser(userDto.getUserName(), userDto.getPassword());
        return "redirect:/login";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(Model model,
                                 ForgotPasswordDto forgotPasswordDto) {

        Optional<User> maybeUser = userService.findUserByName(forgotPasswordDto.getUsername());
        model.addAttribute("forgotPassword", forgotPasswordDto);

        if (maybeUser.isPresent()) {
            UUID token = userService.resetPasswordToken(maybeUser.get());
            model.addAttribute("url", toUrl(token));
        } else {
            model.addAttribute("error", "No such user");
        }

        return "forgot-password";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {

        model.addAttribute("forgotPassword", new ForgotPasswordDto());
        return "forgot-password";
    }

    private String toUrl(UUID token) {
        return "http://localhost:8080/reset-password/" + token.toString();
    }

    @GetMapping("/reset-password/{token}")
    public String resetPassword(Model model, @PathVariable String token) {

        model.addAttribute("resetPassword", new ResetPasswordDto(token));

        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute("resetPassword") ResetPasswordDto resetPasswordDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "reset-password";
        }

        userService.resetPassword(UUID.fromString(resetPasswordDto.getToken()), resetPasswordDto.getPassword());
        return "login";
    }
}
