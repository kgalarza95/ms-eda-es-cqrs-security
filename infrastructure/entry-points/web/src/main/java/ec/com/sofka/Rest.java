package ec.com.sofka;

import ec.com.sofka.data.RequestDTO;
import ec.com.sofka.data.ResponseDTO;
import ec.com.sofka.handlers.AccountHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class Rest {
    private final AccountHandler handler;

    public Rest(AccountHandler handler) {
        this.handler = handler;
    }


    @GetMapping
    public ResponseEntity<List<ResponseDTO>> getAllAccounts(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(handler.getAllAccounts());
    }

    @PostMapping("/number")
    public ResponseEntity<ResponseDTO> getAccountByNumber(@RequestBody RequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(handler.getAccountByNumber(requestDTO));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody RequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(handler.createAccount(requestDTO));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateAccount(@RequestBody RequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(handler.updateAccount(requestDTO));
    }

    @PutMapping("/status")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestBody RequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(handler.deleteAccount(requestDTO));
    }
}
