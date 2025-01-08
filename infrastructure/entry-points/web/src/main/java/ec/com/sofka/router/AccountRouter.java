package ec.com.sofka.router;

import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.handlers.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Configuration
public class AccountRouter {

    private final AccountHandler handler;

    public AccountRouter(AccountHandler handler) {
        this.handler = handler;
    }


    @Bean
    public RouterFunction<ServerResponse> accountFunctionRoute() {
        return RouterFunctions.route()
                .GET("/api/accounts", this::getAllAccounts)
                .POST("/api/accounts/number", this::getAccountByNumber)
                .POST("/api/accounts", this::createAccount)
                .PUT("/api/accounts", this::updateAccount)
                .PUT("/api/accounts/status", this::deleteAccount)
                .build();
    }

    private Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return handler.getAllAccounts()
                .collectList()
                .flatMap(accounts -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accounts));
    }

    private Mono<ServerResponse> getAccountByNumber(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(handler::getAccountByNumber)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    private Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(handler::createAccount)
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    private Mono<ServerResponse> updateAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(handler::updateAccount)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    private Mono<ServerResponse> deleteAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(handler::deleteAccount)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

//    @GetMapping
//    public ResponseEntity<List<ResponseDTO>> getAllAccounts(){
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(handler.getAllAccounts());
//    }
//
//    @PostMapping("/number")
//    public ResponseEntity<ResponseDTO> getAccountByNumber(@RequestBody RequestDTO requestDTO){
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(handler.getAccountByNumber(requestDTO));
//    }
//
//    @PostMapping
//    public ResponseEntity<ResponseDTO> createAccount(@RequestBody RequestDTO requestDTO){
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(handler.createAccount(requestDTO));
//    }
//
//    @PutMapping
//    public ResponseEntity<ResponseDTO> updateAccount(@RequestBody RequestDTO requestDTO){
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(handler.updateAccount(requestDTO));
//    }
//
//    @PutMapping("/status")
//    public ResponseEntity<ResponseDTO> deleteAccount(@RequestBody RequestDTO requestDTO){
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(handler.deleteAccount(requestDTO));
//    }
}
