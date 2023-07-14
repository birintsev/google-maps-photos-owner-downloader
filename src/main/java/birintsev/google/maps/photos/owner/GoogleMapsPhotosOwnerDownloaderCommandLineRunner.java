package birintsev.google.maps.photos.owner;

import birintsev.google.maps.photos.owner.annotations.InputQuery;
import birintsev.google.maps.photos.owner.dto.GooglePhotoDownload;
import birintsev.google.maps.photos.owner.services.GooglePhotoDownloadSaver;
import birintsev.google.maps.photos.owner.services.GooglePlacesApiService;
import birintsev.google.maps.photos.owner.services.IsByOwnerPhotoPredicate;
import com.google.maps.model.Photo;
import com.google.maps.model.PlaceDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Validated
public class GoogleMapsPhotosOwnerDownloaderCommandLineRunner implements CommandLineRunner {

    @InputQuery
    private final String googlePlacesQuery;

    private final GooglePlacesApiService googlePlacesApiService;

    private final IsByOwnerPhotoPredicate isByOwnerPhotoPredicate;

    private final GooglePhotoDownloadSaver googlePhotoDownloadSaver;

    @Override
    public void run(String... args) {
        downloadAndSavePhotos();
    }

    public void downloadAndSavePhotos() {
        googlePlacesApiService.textSearchQueryAllAsPlaceDetails(googlePlacesQuery)
            .parallelStream()
            .flatMap(this::splitIntoDownloadsDtoStream)
            .filter(this::isByOwnerPhoto)
            .forEach(googlePhotoDownloadSaver::save);
    }

    private boolean isByOwnerPhoto(GooglePhotoDownload googlePhotoDownload) {
        return isByOwnerPhoto(googlePhotoDownload.getPlaceDetails(), googlePhotoDownload.getPhoto());
    }

    private boolean isByOwnerPhoto(PlaceDetails placeDetails, Photo photo) {
        return isByOwnerPhotoPredicate.isByOwnerPhoto(placeDetails, photo);
    }

    private List<GooglePhotoDownload> splitIntoDownloadsDto(PlaceDetails placeDetails) {
        return Arrays.stream(placeDetails.photos)
            .map(photo -> {
                GooglePhotoDownload processingDto = new GooglePhotoDownload();
                processingDto.setPlaceDetails(placeDetails);
                processingDto.setPhoto(photo);
                processingDto.setImageResult(googlePlacesApiService.downloadPhoto(photo));
                return processingDto;
            }).toList();
    }

    private Stream<GooglePhotoDownload> splitIntoDownloadsDtoStream(PlaceDetails placeDetails) {
        return splitIntoDownloadsDto(placeDetails).stream();
    }
}
