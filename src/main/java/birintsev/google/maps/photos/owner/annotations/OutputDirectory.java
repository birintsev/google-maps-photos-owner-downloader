package birintsev.google.maps.photos.owner.annotations;

import birintsev.google.maps.photos.owner.validators.OutputDirectoryValidator;
import jakarta.validation.Constraint;
import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Value("${output.directory.path}")
@Constraint(validatedBy = OutputDirectoryValidator.class)
public @interface OutputDirectory {

}
