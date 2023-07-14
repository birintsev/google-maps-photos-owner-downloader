package birintsev.google.maps.photos.owner.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PathUtils {

    private static final String NON_FULLY_POSIX_PORTABLE_CHARACTER_REGEX = "[^A-Za-z0-9._-]";

    private static final String NON_FULLY_POSIX_PORTABLE_CHARACTER_REPLACEMENT = "_";

    public static String escape(String pathSegment) {
        return pathSegment.replaceAll(
            NON_FULLY_POSIX_PORTABLE_CHARACTER_REGEX,
            NON_FULLY_POSIX_PORTABLE_CHARACTER_REPLACEMENT
        );
    }
}
