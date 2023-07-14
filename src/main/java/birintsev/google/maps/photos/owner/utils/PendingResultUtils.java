package birintsev.google.maps.photos.owner.utils;

import birintsev.google.maps.photos.owner.exceptions.DownloaderRuntimeException;
import com.google.maps.PendingResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PendingResultUtils {

    public static <T> T tryAwait(PendingResult<T> pendingResult) {
        try {
            return pendingResult.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DownloaderRuntimeException(e);
        } catch (Exception e) {
            throw new DownloaderRuntimeException(e);
        }
    }
}
