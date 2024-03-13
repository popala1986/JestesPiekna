package pl.JestesPiekna.photoGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.JestesPiekna.model.PhotoGallery;

@Repository
public interface PhotoGalleryRepository extends JpaRepository<PhotoGallery, Long> {

}