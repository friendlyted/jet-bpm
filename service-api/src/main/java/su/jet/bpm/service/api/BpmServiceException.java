package su.jet.bpm.service.api;

/**
 * @author Fedor Resnyanskiy <fa.resnyanskiy@jet.msk.su>
 */
public class BpmServiceException extends Exception {
    public BpmServiceException() {
    }

    public BpmServiceException(String message) {
        super(message);
    }

    public BpmServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BpmServiceException(Throwable cause) {
        super(cause);
    }

    public BpmServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
