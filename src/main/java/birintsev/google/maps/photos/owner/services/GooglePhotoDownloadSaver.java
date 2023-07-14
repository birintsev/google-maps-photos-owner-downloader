package birintsev.google.maps.photos.owner.services;

import birintsev.google.maps.photos.owner.annotations.CompletedDownload;
import birintsev.google.maps.photos.owner.dto.GooglePhotoDownload;
import jakarta.validation.Valid;

import java.nio.file.Path;

public interface GooglePhotoDownloadSaver {

    Path save(@Valid  @CompletedDownload GooglePhotoDownload googlePhotoDownload);
}
