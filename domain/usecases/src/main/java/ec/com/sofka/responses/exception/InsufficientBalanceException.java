package ec.com.sofka.responses.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String mensaje) {
        super(mensaje);
    }
}
