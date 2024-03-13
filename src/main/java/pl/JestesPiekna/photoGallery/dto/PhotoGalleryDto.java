package pl.JestesPiekna.photoGallery.dto;

import java.util.List;

public class PhotoGalleryDto {

    private List<byte[]> uploadedPhotos;

    public PhotoGalleryDto(List<byte[]> uploadedPhotos) {
        this.uploadedPhotos = uploadedPhotos;
    }

    public PhotoGalleryDto() {
    }

    public List<byte[]> getUploadedPhotos() {
        return uploadedPhotos;
    }

    public void setUploadedPhotos(List<byte[]> uploadedPhotos) {
        this.uploadedPhotos = uploadedPhotos;
    }
}
