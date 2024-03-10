package pl.JestesPiekna.registration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.JestesPiekna.registration.dto.RegisterUserDto;

@Controller
public class RegistrationController {

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerUserDto", new RegisterUserDto());
        return "registrationForm";
    }
}
