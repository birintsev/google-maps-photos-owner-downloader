package birintsev.google.maps.photos.owner.annotations;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Value("${input.google.cloud.places.query}")
@NotBlank(message = "Google Places search query must not be blank")
public @interface InputQuery {

}
