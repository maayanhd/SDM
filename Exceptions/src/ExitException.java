public class ExitException extends Exception {

    private String reason;

    public ExitException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public ExitException(String message) {
        super(message);
    }
}
