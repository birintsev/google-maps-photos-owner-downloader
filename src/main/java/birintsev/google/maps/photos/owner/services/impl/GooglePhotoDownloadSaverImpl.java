package birintsev.google.maps.photos.owner.services.impl;

import birintsev.google.maps.photos.owner.annotations.CompletedDownload;
import birintsev.google.maps.photos.owner.annotations.OutputDirectory;
import birintsev.google.maps.photos.owner.dto.GooglePhotoDownload;
import birintsev.google.maps.photos.owner.exceptions.DownloaderRuntimeException;
import birintsev.google.maps.photos.owner.services.GooglePhotoDownloadSaver;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static birintsev.google.maps.photos.owner.utils.PathUtils.escape;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Component
@RequiredArgsConstructor
@Validated
public class GooglePhotoDownloadSaverImpl implements GooglePhotoDownloadSaver {

    @OutputDirectory
    private final Path outputDirectory;

    @Override
    public Path save(@Valid @CompletedDownload GooglePhotoDownload googlePhotoDownload) {
        Path targetFilePath = null;
        try {
            targetFilePath = getPhotoPathAndCreateDirectories(googlePhotoDownload);
            return Files.write(targetFilePath, googlePhotoDownload.getImageResult().imageData, CREATE_NEW);
        } catch (IOException e) {
            throw new DownloaderRuntimeException(
                "Failed to write bytes to " + targetFilePath + " for " + googlePhotoDownload,
                e
            );
        }
    }

    private Path getPhotoPath(GooglePhotoDownload googlePhotoDownload) {
        return outputDirectory
            .resolve(escape(googlePhotoDownload.getPlaceDetails().formattedAddress))
            .resolve(escape(googlePhotoDownload.getPhoto().photoReference));
    }

    private Path getPhotoPathAndCreateDirectories(GooglePhotoDownload googlePhotoDownload) throws IOException {
        Path photoPath = getPhotoPath(googlePhotoDownload);
        Files.createDirectories(photoPath.getParent());
        return photoPath;
    }

    @PostConstruct
    private void postConstruct() throws IOException {
        createOutputDirectory();
    }

    private void createOutputDirectory() throws IOException {
        Files.createDirectories(outputDirectory);
    }
}
