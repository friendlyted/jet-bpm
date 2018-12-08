package su.jet.bpm.camel.component;

public class NoBpmServiceException extends Exception {
    public NoBpmServiceException() {
    }

    public NoBpmServiceException(String message) {
        super(message);
    }

    public NoBpmServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoBpmServiceException(Throwable cause) {
        super(cause);
    }

    public NoBpmServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
