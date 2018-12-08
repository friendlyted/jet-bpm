package su.jet.bpm.camel.component;

public class UnsupportedBpmCaseException extends Exception {
    public UnsupportedBpmCaseException() {
    }

    public UnsupportedBpmCaseException(String message) {
        super(message);
    }

    public UnsupportedBpmCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedBpmCaseException(Throwable cause) {
        super(cause);
    }

    public UnsupportedBpmCaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
