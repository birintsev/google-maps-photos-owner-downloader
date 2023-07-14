package birintsev.google.maps.photos.owner.services;

import birintsev.google.maps.photos.owner.dto.GooglePhotoDownload;

import java.nio.file.Path;

public interface GooglePhotoDownloadSaver {

    Path save(GooglePhotoDownload googlePhotoDownload);
}
