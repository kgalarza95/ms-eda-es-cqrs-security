package ec.com.sofka.dto;

public class JwtRequestDTO {

    private String email;
    private String password;

    public JwtRequestDTO() {
    }

    public JwtRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
