package pl.JestesPiekna.homePage.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.JestesPiekna.model.PhotoGallery;
import pl.JestesPiekna.photoGallery.service.PhotoGalleryService;
import pl.JestesPiekna.reservation.service.ReservationService;

import java.util.List;


@Controller
public class HomePageController {

    private final PhotoGalleryService photoGalleryService;

    private final ReservationService reservationService;

    public HomePageController(PhotoGalleryService photoGalleryService, ReservationService reservationService) {
        this.photoGalleryService = photoGalleryService;
        this.reservationService = reservationService;
    }


    @GetMapping("/YouAreBeautiful")
    public String showHomePageAll(Model model) {
        List<PhotoGallery> latestPhotos  = photoGalleryService.getLatestPhotos();
        model.addAttribute("latestPhotos", latestPhotos);
        return "homePageAll";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/homePage")
    public String showHomePage(Model model) {

        String username = reservationService.getUsernameFromContext();
        if (username != null) {

            model.addAttribute("username", username);
        }

        List<PhotoGallery> latestPhotos  = photoGalleryService.getLatestPhotos();
        model.addAttribute("latestPhotos", latestPhotos);
        return "homePage";
    }
}
