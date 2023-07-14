package birintsev.google.maps.photos.owner.services;

import com.google.maps.ImageResult;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResult;

import java.util.List;

public interface GooglePlacesApiService {

    List<PlacesSearchResult> textSearchQueryAll(String query);

    ImageResult downloadPhoto(Photo photo);
}
