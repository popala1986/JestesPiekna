package pl.JestesPiekna.photoGallery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.JestesPiekna.photoGallery.repository.PhotoGalleryRepository;
import pl.JestesPiekna.photoGallery.service.PhotoGalleryService;

import java.util.List;

@RestController
public class PhotoController {

    private final PhotoGalleryRepository photoGalleryRepository;

    private final PhotoGalleryService photoGalleryService;

    public PhotoController(PhotoGalleryRepository photoGalleryRepository, PhotoGalleryService photoGalleryService) {
        this.photoGalleryRepository = photoGalleryRepository;
        this.photoGalleryService = photoGalleryService;
    }


    @GetMapping("/photos/{id}")
    public ResponseEntity<byte[]> getPhotoById(@PathVariable Integer id) {
        List<byte[]> uploadedPhotos = photoGalleryRepository.findUploadedPhotosById(id);
        if (!((List<?>) uploadedPhotos).isEmpty()) {
            byte[] imageData = uploadedPhotos.get(0); // Załóżmy, że pobieramy pierwsze zdjęcie z listy
            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}