package pl.JestesPiekna.photoGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.JestesPiekna.model.PhotoGallery;

import java.util.List;

@Repository
public interface PhotoGalleryRepository extends JpaRepository<PhotoGallery, Integer> {

    @Query(value = "SELECT pg.uploadedPhotos FROM PhotoGallery pg WHERE pg.id = :id")
    List<byte[]> findUploadedPhotosById(@Param("id") Integer id);

    List<PhotoGallery> findAll();

    @Query(value = "SELECT p FROM PhotoGallery p ORDER BY p.id DESC")
    List<PhotoGallery> findTop3ByOrderByIdDesc();
}