package ec.com.sofka.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String mensaje) {
        super(mensaje);
    }
}
