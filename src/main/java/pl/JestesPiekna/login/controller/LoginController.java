package pl.JestesPiekna.login.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.JestesPiekna.login.dto.LoginDto;
import pl.JestesPiekna.login.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;


    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("LoginDto", new LoginDto());
        return "loginForm";
    }

}
