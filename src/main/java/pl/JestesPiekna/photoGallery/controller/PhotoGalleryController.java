package pl.JestesPiekna.photoGallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.JestesPiekna.photoGallery.service.PhotoGalleryService;

@Controller
public class PhotoGalleryController {

    private final PhotoGalleryService photoGalleryService;

    public PhotoGalleryController(PhotoGalleryService photoGalleryService) {
        this.photoGalleryService = photoGalleryService;
    }

    @GetMapping("/uploadPhoto")
    public String showUploadPhotoPage() {
        return "uploadPhoto";
    }

    @PostMapping("/photo/upload")
    public String uploadPhotos(@RequestParam("photos") MultipartFile[] photos, RedirectAttributes redirectAttributes) {
        photoGalleryService.savePhotos(photos);
        redirectAttributes.addFlashAttribute("message", "Photos uploaded successfully!");
        return "redirect:/photo/upload-success";
    }

    @GetMapping("/photo/upload-success")
    public String showUploadSuccessPage() {
        return "upload-success";
    }
}