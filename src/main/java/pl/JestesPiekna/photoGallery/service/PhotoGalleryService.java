package pl.JestesPiekna.photoGallery.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    @Transactional
    public void savePhotos(MultipartFile[] photos) {
        try {
            List<byte[]> photoBytesList = new ArrayList<>();
            for (MultipartFile photo : photos) {
                byte[] bytes = photo.getBytes();
                photoBytesList.add(bytes);
            }

            PhotoGallery photoGallery = new PhotoGallery();
            photoGallery.setUploadedPhotos(photoBytesList);

            photoGalleryRepository.save(photoGallery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}