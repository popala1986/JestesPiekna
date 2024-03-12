package pl.JestesPiekna.registration.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.JestesPiekna.registration.dto.RegisterUserDto;
import pl.JestesPiekna.registration.exception.*;
import pl.JestesPiekna.registration.service.RegistrationService;

@Controller
public class RegistrationController {

    public final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerUserDto", new RegisterUserDto());
        return "registrationForm";
    }

    @PostMapping("/registration")
    public String registerNewUser(
            @ModelAttribute("registerUserDto") @Valid RegisterUserDto registerUserDto,
            BindingResult bindingResult,
            Model model) {

        try {
            registrationService.registerUser(registerUserDto);
            return "redirect:/login";

        } catch (InvalidUsernameException ex) {
            bindingResult.rejectValue("username", "error.registerUserDto", ex.getMessage());


        } catch (InvalidPasswordException ex) {
            bindingResult.rejectValue("password", "error.registerUserDto", ex.getMessage());

        } catch (InvalidEmailException ex) {
            bindingResult.rejectValue("email", "error.registerDto", ex.getMessage());

        } catch (InvalidFirstNameLenghtException ex) {
            bindingResult.rejectValue("firstName", "error.registerDto", ex.getMessage());

        }catch (InvalidLastNameLenghtException ex) {
            bindingResult.rejectValue("lastName", "error.registerDto", ex.getMessage());

        } catch (InvalidPhoneNumberLenghtException ex) {
            bindingResult.rejectValue("phone","error.registerDto", ex.getMessage());

        } catch (UserAlreadyExistsException ex) {
            bindingResult.rejectValue("username", "error.registerUserDto", ex.getMessage());
            model.addAttribute("userExistsError", "This username is already taken");

        } catch (EmailAlreadyExistsException ex) {
            bindingResult.rejectValue("email", "error.registerUserDto", ex.getMessage());
            model.addAttribute("emailExistsError", "This email is already taken");

        } catch (PhoneNumberAlreadyExistsException ex) {
            bindingResult.rejectValue("phone", "error.registerUserDto", ex.getMessage());
            model.addAttribute("phoneExistsError", "This phone number is already use");
        }

        return "registrationForm";

    }
}
