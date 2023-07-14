package birintsev.google.maps.photos.owner.annotations;

import birintsev.google.maps.photos.owner.validators.CompletedDownloadValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CompletedDownloadValidator.class)
public @interface CompletedDownload {

    String message() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
