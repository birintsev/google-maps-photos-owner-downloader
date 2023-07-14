package birintsev.google.maps.photos.owner.validators;

import birintsev.google.maps.photos.owner.annotations.CompletedDownload;
import birintsev.google.maps.photos.owner.dto.GooglePhotoDownload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompletedDownloadValidator implements ConstraintValidator<CompletedDownload, GooglePhotoDownload> {

    @Override
    public boolean isValid(GooglePhotoDownload googlePhotoDownload, ConstraintValidatorContext context) {
        if (googlePhotoDownload == null) {
            context
                .buildConstraintViolationWithTemplate("Google Photo Download must not be null")
                .addConstraintViolation();

            return false;
        }

        if (googlePhotoDownload.getImageResult() == null) {
            context
                .buildConstraintViolationWithTemplate(
                    "The image has not been downloaded yet for " + googlePhotoDownload
                )
                .addConstraintViolation();

            return false;
        }

        return true;
    }
}
