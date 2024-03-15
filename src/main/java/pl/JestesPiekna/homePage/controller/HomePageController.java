package pl.JestesPiekna.homePage.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.JestesPiekna.model.PhotoGallery;
import pl.JestesPiekna.photoGallery.service.PhotoGalleryService;

import java.util.List;


@Controller
public class HomePageController {

    private final PhotoGalleryService photoGalleryService;

    public HomePageController(PhotoGalleryService photoGalleryService) {
        this.photoGalleryService = photoGalleryService;
    }


    @GetMapping("/YouAreBeautiful")
    public String showHomePage(Model model) {
        List<PhotoGallery> latestPhotos  = photoGalleryService.getLatestPhotos();
        model.addAttribute("latestPhotos", latestPhotos);
        return "homePageAll";
    }

    @GetMapping("/homePage")
    public String showMainPage(Model model) {
        List<PhotoGallery> latestPhotos  = photoGalleryService.getLatestPhotos();
        model.addAttribute("latestPhotos", latestPhotos);
        return "homePage";
    }
}
