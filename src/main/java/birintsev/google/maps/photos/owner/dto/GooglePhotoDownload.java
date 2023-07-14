package birintsev.google.maps.photos.owner.dto;

import com.google.maps.ImageResult;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class GooglePhotoDownload {

    private PlacesSearchResult placesSearchResult;

    private ImageResult imageResult;

    private Photo photo;
}
