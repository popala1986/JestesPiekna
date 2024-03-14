package pl.JestesPiekna.photoGallery.service;

import org.springframework.stereotype.Service;
import pl.JestesPiekna.model.PhotoGallery;
import pl.JestesPiekna.photoGallery.repository.PhotoGalleryRepository;


import java.util.List;

@Service
public class PhotoGalleryService {

    private final PhotoGalleryRepository photoGalleryRepository;

    public PhotoGalleryService(PhotoGalleryRepository photoGalleryRepository) {
        this.photoGalleryRepository = photoGalleryRepository;
    }



    public void savePhotos(PhotoGallery photoGallery) {
        photoGalleryRepository.save(photoGallery);
    }

    public List<PhotoGallery> getAllPhotos() {
        return photoGalleryRepository.findAll();
    }


    public List<PhotoGallery> getLatestPhotos() {
        return photoGalleryRepository.findTop3ByOrderByIdDesc();
    }
}