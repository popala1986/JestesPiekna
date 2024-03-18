package pl.JestesPiekna.contact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String showContactForm() {
        return "contact";
    }

    @GetMapping("/contactAll")
    public String showContactAllForm() {
        return "contactAll";
    }
}
