package ec.com.sofka.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String mensaje) {
        super(mensaje);
    }
}
