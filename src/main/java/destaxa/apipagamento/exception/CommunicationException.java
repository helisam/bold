package destaxa.apipagamento.exception;

public class CommunicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommunicationException(String message) {
        super(message);
    }

    public CommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}