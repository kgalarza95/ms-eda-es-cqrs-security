package ec.com.sofka.router;

import ec.com.sofka.dto.TransactionInDTO;
import ec.com.sofka.handlers.TransactionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class TransactionRouter {

    private final TransactionHandler transactionHandler;

    public TransactionRouter(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }


    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/v1/api/movimientos/deposito/sucursal",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "makeBranchDeposit",
                            summary = "Make deposit at branch",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = TransactionInDTO.class))),
                            responses = {@ApiResponse(responseCode = "201", description = "Deposit completed")}
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/movimientos/deposito/cajero",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "makeATMDeposit",
                            summary = "Make ATM deposit",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = TransactionInDTO.class)))
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/movimientos/deposito/otra-cuenta",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "makeDepositToAnotherAccount",
                            summary = "Transfer to another account",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = TransactionInDTO.class)))
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/movimientos/compra/fisica",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "makePhysicalPurchase",
                            summary = "Make in-store purchase",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = TransactionInDTO.class)))
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/movimientos/compra/web",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "makeOnlinePurchase",
                            summary = "Make online purchase",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = TransactionInDTO.class)))
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/movimientos/retiro/cajero",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "makeATMWithdrawal",
                            summary = "Make ATM withdrawal",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = TransactionInDTO.class)))
                    )
            )
    })
    public RouterFunction<ServerResponse> TransactionRouterBean() {
        return RouterFunctions.route()
                .POST("/v1/api/movimientos/deposito/sucursal", this::makeBranchDeposit)
                .POST("/v1/api/movimientos/deposito/cajero", this::makeATMDeposit)
                .POST("/v1/api/movimientos/deposito/otra-cuenta", this::makeDepositToAnotherAccount)
                .POST("/v1/api/movimientos/compra/fisica", this::makePhysicalPurchase)
                .POST("/v1/api/movimientos/compra/web", this::makeOnlinePurchase)
                .POST("/v1/api/movimientos/retiro/cajero", this::makeATMWithdrawal)
                .build();
    }

    public Mono<ServerResponse> makeBranchDeposit(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeBranchDeposit)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makeATMDeposit(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeATMDeposit)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makeDepositToAnotherAccount(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeDepositToAnotherAccount)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makePhysicalPurchase(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makePhysicalPurchase)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makeOnlinePurchase(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeOnlinePurchase)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makeATMWithdrawal(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeATMWithdrawal)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }
}
