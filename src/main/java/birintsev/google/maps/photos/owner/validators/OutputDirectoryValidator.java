package birintsev.google.maps.photos.owner.validators;

import birintsev.google.maps.photos.owner.annotations.OutputDirectory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.nio.file.Path;

import static java.nio.file.Files.exists;

public class OutputDirectoryValidator implements ConstraintValidator<OutputDirectory, Path> {

    @Override
    public boolean isValid(Path path, ConstraintValidatorContext context) {
        if (path == null) {
            context
                .buildConstraintViolationWithTemplate("Output directory must be specified")
                .addConstraintViolation();
            return false;
        }

        if (exists(path)) {
            context
                .buildConstraintViolationWithTemplate("Output directory already exists")
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}
