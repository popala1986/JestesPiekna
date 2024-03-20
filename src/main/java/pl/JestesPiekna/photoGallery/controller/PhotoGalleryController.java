package pl.JestesPiekna.photoGallery.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.JestesPiekna.model.PhotoGallery;
import pl.JestesPiekna.photoGallery.service.PhotoGalleryService;

import java.util.List;

@Controller
public class PhotoGalleryController {

    private final PhotoGalleryService photoGalleryService;

    public PhotoGalleryController(PhotoGalleryService photoGalleryService) {
        this.photoGalleryService = photoGalleryService;
    }

    @GetMapping("/gallery")
    public String showGallery(Model model) {
        List<PhotoGallery> AllPhotos  = photoGalleryService.getAllPhotos();
        model.addAttribute("AllPhotos", AllPhotos);
        return "galleryPage";
    }
}
