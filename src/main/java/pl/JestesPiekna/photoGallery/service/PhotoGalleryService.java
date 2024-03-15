package pl.JestesPiekna.photoGallery.service;

import org.springframework.stereotype.Service;
import pl.JestesPiekna.model.PhotoGallery;
import pl.JestesPiekna.photoGallery.repository.PhotoGalleryRepository;


import java.io.IOException;
import java.util.ArrayList;
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
        List<PhotoGallery> latestPhotos = photoGalleryRepository.findTop3ByOrderByIdDesc();
        if (latestPhotos.size() > 3) {
            latestPhotos = latestPhotos.subList(0, 3);
        }
        return latestPhotos;
    }
}