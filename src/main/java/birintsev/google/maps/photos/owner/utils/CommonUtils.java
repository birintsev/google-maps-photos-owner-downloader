package birintsev.google.maps.photos.owner.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtils {

    public static boolean containsAny(Collection<?> collection) {
        return collection != null && collection.size() != 0;
    }

    public static boolean containsAny(Object[] objects) {
        return objects != null && objects.length != 0;
    }
}
