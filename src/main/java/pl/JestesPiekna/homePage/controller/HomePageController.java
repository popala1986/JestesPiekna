package pl.JestesPiekna.homePage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomePageController {

    @GetMapping("/YouAreBeautiful")
    public String showHomePage() {
        return "homePageAll";
    }

    @GetMapping("/securedMainPage")
    public String showMainPage() {
        return "homePage";
    }
}
