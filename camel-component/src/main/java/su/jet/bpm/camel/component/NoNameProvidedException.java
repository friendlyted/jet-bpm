package su.jet.bpm.camel.component;

public class NoNameProvidedException extends Exception {
    public NoNameProvidedException() {
    }

    public NoNameProvidedException(String message) {
        super(message);
    }

    public NoNameProvidedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNameProvidedException(Throwable cause) {
        super(cause);
    }

    public NoNameProvidedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
