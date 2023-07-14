package birintsev.google.maps.photos.owner.services;

import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResult;

public interface IsByOwnerPhotoPredicate {

    boolean isByOwnerPhoto(PlacesSearchResult placesSearchResult, Photo photo);
}
