package birintsev.google.maps.photos.owner.exceptions;

public class DownloaderRuntimeException extends RuntimeException {

    public DownloaderRuntimeException() {
    }

    public DownloaderRuntimeException(String message) {
        super(message);
    }

    public DownloaderRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloaderRuntimeException(Throwable cause) {
        super(cause);
    }

    public DownloaderRuntimeException(
        String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
