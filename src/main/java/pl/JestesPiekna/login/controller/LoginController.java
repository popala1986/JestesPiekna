package pl.JestesPiekna.login.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.JestesPiekna.login.dto.LoginDto;
import pl.JestesPiekna.login.exception.InvalidUsernameOrPasswordException;
import pl.JestesPiekna.login.exception.PasswordIncorrectException;
import pl.JestesPiekna.login.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;


    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("loginDto", new LoginDto());
        return "loginForm";
    }

    @PostMapping("/login")
    public String logIn(
            @ModelAttribute("loginDto") @Valid LoginDto loginDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Invalid username or password");
            return "loginForm";
        }

        try {
            if (loginService.isPasswordCorrect(loginDto.getUsername(), loginDto.getPassword())) {
                return "homePage";
            } else {
                throw new PasswordIncorrectException("Invalid username or password");
            }
        } catch (InvalidUsernameOrPasswordException ex) {
            bindingResult.rejectValue("username", "error.loginDto", ex.getMessage());
            model.addAttribute("usernameError", "Invalid username or password");
        } catch (PasswordIncorrectException ex) {
            bindingResult.rejectValue("password", "error.loginDto", ex.getMessage());
            model.addAttribute("passwordError", "Invalid username or password");
        }
        return "loginForm";
    }

}
