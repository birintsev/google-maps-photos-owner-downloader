package birintsev.google.maps.photos.owner.services.impl;

import birintsev.google.maps.photos.owner.annotations.CompletedDownload;
import birintsev.google.maps.photos.owner.annotations.OutputDirectory;
import birintsev.google.maps.photos.owner.dto.GooglePhotoDownload;
import birintsev.google.maps.photos.owner.exceptions.DownloaderRuntimeException;
import birintsev.google.maps.photos.owner.services.GooglePhotoDownloadSaver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Component
@RequiredArgsConstructor
@Validated
public class GooglePhotoDownloadSaverImpl implements GooglePhotoDownloadSaver {

    @OutputDirectory
    private final Path outputDirectory;

    @Override
    public Path save(@CompletedDownload GooglePhotoDownload googlePhotoDownload) {
        Path targetFilePath = outputDirectory.resolve(getPhotoFileName(googlePhotoDownload));
        try {
            return Files.write(targetFilePath, googlePhotoDownload.getImageResult().imageData, CREATE_NEW);
        } catch (IOException e) {
            throw new DownloaderRuntimeException(
                "Failed to write bytes to " + targetFilePath + " for " + googlePhotoDownload,
                e
            );
        }
    }

    private String getPhotoFileName(GooglePhotoDownload googlePhotoDownload) {
        return googlePhotoDownload.getPhoto().photoReference
            + " (" + googlePhotoDownload.getImageResult().contentType + ")";
    }
}
