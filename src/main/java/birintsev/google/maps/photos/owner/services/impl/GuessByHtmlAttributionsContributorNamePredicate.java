package birintsev.google.maps.photos.owner.services.impl;

import birintsev.google.maps.photos.owner.services.IsByOwnerPhotoPredicate;
import com.google.maps.model.Photo;
import com.google.maps.model.PlaceDetails;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import static birintsev.google.maps.photos.owner.constants.Constants.HTML_TAG_LINK;
import static birintsev.google.maps.photos.owner.utils.CommonUtils.containsAny;

@Component
public class GuessByHtmlAttributionsContributorNamePredicate implements IsByOwnerPhotoPredicate {

    @Override
    public boolean isByOwnerPhoto(PlaceDetails placeDetails, Photo photo) {
        String placeName = placeDetails.name;
        String photoContributorName = extractPhotoContributorName(photo);
        return placeName != null && placeName.equals(photoContributorName);
    }

    private String extractPhotoContributorName(Photo photo) {
        String[] htmlAttributions = photo.htmlAttributions;
        Elements parsedHtmlAttributions;
        Element photoContributionLink;

        if (!containsAny(htmlAttributions)) {
            return null;
        }

        parsedHtmlAttributions = Jsoup.parse(photo.htmlAttributions[0]).getElementsByTag(HTML_TAG_LINK);
        if (!containsAny(parsedHtmlAttributions)) {
            return null;
        }

        photoContributionLink = parsedHtmlAttributions.get(0);
        return photoContributionLink == null ? null : photoContributionLink.text();
    }
}
