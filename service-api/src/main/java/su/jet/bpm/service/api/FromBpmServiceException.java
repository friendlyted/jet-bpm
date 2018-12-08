package su.jet.bpm.service.api;

/**
 * @author Fedor Resnyanskiy <fa.resnyanskiy@jet.msk.su>
 */
public class FromBpmServiceException extends Exception {
    public FromBpmServiceException() {
    }

    public FromBpmServiceException(String message) {
        super(message);
    }

    public FromBpmServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FromBpmServiceException(Throwable cause) {
        super(cause);
    }

    public FromBpmServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
