package pl.JestesPiekna.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "photo_gallery")
public class PhotoGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Lob
    @Column(length = 10485760)
    private List<byte[]> uploadedPhotos;

    public PhotoGallery(Integer id, List<byte[]> uploadedPhotos) {
        this.id = id;
        this.uploadedPhotos = uploadedPhotos;
    }

    public PhotoGallery() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<byte[]> getUploadedPhotos() {
        return uploadedPhotos;
    }

    public void setUploadedPhotos(List<byte[]> uploadedPhotos) {
        this.uploadedPhotos = uploadedPhotos;
    }
}