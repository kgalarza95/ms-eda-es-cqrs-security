package ec.com.sofka.data;

public class ResponseDTO {
    public String customerId;
    public String accountName;

    public ResponseDTO(String customerId, String accountName) {
        this.customerId = customerId;
        this.accountName = accountName;
    }


    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

}
