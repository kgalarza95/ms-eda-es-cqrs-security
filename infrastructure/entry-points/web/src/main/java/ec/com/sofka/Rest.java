package ec.com.sofka;

import ec.com.sofka.account.Account;
import ec.com.sofka.data.RequestDTO;
import ec.com.sofka.data.ResponseDTO;
import ec.com.sofka.handlers.AccountHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Rest {
    private final AccountHandler handler;

    public Rest(AccountHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/account")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody RequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(handler.createAccount(requestDTO));
    }
}
