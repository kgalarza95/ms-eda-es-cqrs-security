package ec.com.sofka.gateway.dto;

import java.time.LocalDate;

public class LogDTO {

    private String id;
    private String message;
    private LocalDate registrationDate;

    public LogDTO() {
    }

    public LogDTO(String message, LocalDate registrationDate) {
        this.message = message;
        this.registrationDate = registrationDate;
    }

    public LogDTO(String id, String message, LocalDate registrationDate) {
        this.id = id;
        this.message = message;
        this.registrationDate = registrationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
