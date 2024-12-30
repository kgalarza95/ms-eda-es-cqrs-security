package ec.com.sofka.data;

public class ResponseDTO {
    public String customer;
    public String account;

    public ResponseDTO(String customer, String account) {
        this.customer = customer;
        this.account = account;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
