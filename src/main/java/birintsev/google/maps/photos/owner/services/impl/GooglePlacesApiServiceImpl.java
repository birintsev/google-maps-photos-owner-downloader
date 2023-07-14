package birintsev.google.maps.photos.owner.services.impl;

import birintsev.google.maps.photos.owner.annotations.InputApiKey;
import birintsev.google.maps.photos.owner.exceptions.DownloaderRuntimeException;
import birintsev.google.maps.photos.owner.services.GooglePlacesApiService;
import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.PlacesApi;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static birintsev.google.maps.photos.owner.utils.PendingResultUtils.tryAwait;

@Service
@RequiredArgsConstructor
@Validated
public class GooglePlacesApiServiceImpl implements GooglePlacesApiService {

    @InputApiKey
    private final String apiKey;

    @Value("${downloader.config.google.maps.places.search-text.next-page-token.delay:PT3S}")
    @NotNull
    private final Duration placesSearchTextNextPageTokenDelay;

    private GeoApiContext geoApiContext;

    @Override
    public List<PlacesSearchResult> textSearchQueryAll(String query) {
        PlacesSearchResponse placesSearchResponse;
        String nextPageToken;
        List<PlacesSearchResult> searchResults;

        placesSearchResponse = tryAwait(PlacesApi.textSearchQuery(geoApiContext, query));
        nextPageToken = placesSearchResponse.nextPageToken;
        searchResults = new ArrayList<>(Arrays.asList(placesSearchResponse.results));
        while (nextPageToken != null) {
            waitNextPageTokenIsValid();
            placesSearchResponse = tryAwait(PlacesApi.textSearchNextPage(geoApiContext, nextPageToken));
            nextPageToken = placesSearchResponse.nextPageToken;
            searchResults.addAll(Arrays.asList(placesSearchResponse.results));
        }

        return searchResults;
    }

    @Override
    public ImageResult downloadPhoto(Photo photo) {
        return tryAwait(PlacesApi.photo(geoApiContext, photo.photoReference));
    }

    @PostConstruct
    private void postConstruct() {
        initializeGeoApiContext();
    }

    @PreDestroy
    private void preDestroy() {
        shutdownGeoApiContext();
    }

    private void initializeGeoApiContext() {
        this.geoApiContext = new GeoApiContext.Builder().apiKey(apiKey).build();
    }

    private void shutdownGeoApiContext() {
        this.geoApiContext.shutdown();
    }

    private void waitNextPageTokenIsValid() {
        try {
            Thread.sleep(placesSearchTextNextPageTokenDelay.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DownloaderRuntimeException(e);
        }
    }
}
