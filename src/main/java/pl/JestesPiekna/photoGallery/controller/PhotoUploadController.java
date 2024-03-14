package pl.JestesPiekna.photoGallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.JestesPiekna.model.PhotoGallery;
import pl.JestesPiekna.photoGallery.service.PhotoGalleryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PhotoUploadController {

    private final PhotoGalleryService photoGalleryService;

    @Autowired
    public PhotoUploadController(PhotoGalleryService photoGalleryService) {
        this.photoGalleryService = photoGalleryService;
    }

    @GetMapping("/uploadForm")
    public String showUploadForm() {
        return "uploadPhoto";
    }

    @GetMapping("/success")
    public String showUploadSuccessForm() {
        return "upload-success";
    }

    @PostMapping("/photo/upload")
    public String uploadPhotos(@RequestParam("photos") MultipartFile[] files, Model model) {
        List<byte[]> uploadedPhotos = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                uploadedPhotos.add(file.getBytes());
            }


            photoGalleryService.savePhotos(new PhotoGallery(uploadedPhotos));


            model.addAttribute("message", "Photos uploaded successfully!");

        } catch (IOException e) {

            model.addAttribute("error", "Error uploading photos: " + e.getMessage());
        }


        return "redirect:/YouAreBeautiful";
    }
}