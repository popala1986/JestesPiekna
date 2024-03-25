package pl.JestesPiekna.photoGallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.JestesPiekna.model.PhotoGallery;
import pl.JestesPiekna.photoGallery.service.PhotoGalleryService;
import pl.JestesPiekna.reservation.service.ReservationService;

import java.util.List;

@Controller
public class PhotoGalleryController {

    private final PhotoGalleryService photoGalleryService;

    private final ReservationService reservationService;

    public PhotoGalleryController(PhotoGalleryService photoGalleryService, ReservationService reservationService) {
        this.photoGalleryService = photoGalleryService;
        this.reservationService = reservationService;
    }

    @GetMapping("/gallery")
    public String showGallery(Model model) {
        List<PhotoGallery> AllPhotos  = photoGalleryService.getAllPhotos();
        model.addAttribute("AllPhotos", AllPhotos);
        return "galleryPage";
    }
}
