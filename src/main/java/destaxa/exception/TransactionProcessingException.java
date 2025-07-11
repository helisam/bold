package destaxa.exception;

public class TransactionProcessingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TransactionProcessingException(String message) {
        super(message);
    }

    public TransactionProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}