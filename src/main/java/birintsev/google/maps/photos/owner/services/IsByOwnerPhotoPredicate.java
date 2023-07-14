package birintsev.google.maps.photos.owner.services;

import com.google.maps.model.Photo;
import com.google.maps.model.PlaceDetails;

public interface IsByOwnerPhotoPredicate {

    boolean isByOwnerPhoto(PlaceDetails placeDetails, Photo photo);
}
