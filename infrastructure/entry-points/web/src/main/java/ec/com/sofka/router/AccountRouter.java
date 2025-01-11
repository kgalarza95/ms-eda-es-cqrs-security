package ec.com.sofka.router;

import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.handlers.AccountHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Configuration
public class AccountRouter {

    private final AccountHandler handler;
    private final String pathBase = "/v1/api";

    public AccountRouter(AccountHandler handler) {
        this.handler = handler;
    }

    @RouterOperations({
            @RouterOperation(
                    path = "/v1/api/accounts",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "createAccount",
                            summary = "Create a new account",
                            description = "This endpoint creates a new account with the provided details.",
                            requestBody = @RequestBody(
                                    description = "Account creation details",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = AccountRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Account created successfully"),
                                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/accounts",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            operationId = "updateAccount",
                            summary = "Update an account",
                            description = "This endpoint updates an existing account with the provided details.",
                            requestBody = @RequestBody(
                                    description = "Account update details",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = AccountRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Account updated successfully"),
                                    @ApiResponse(responseCode = "404", description = "Account not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/accounts/{id}",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getAccountById",
                            summary = "Get account by ID",
                            description = "Retrieve account details by its unique identifier.",
                            parameters = {
                                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of the account", required = true)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Account details retrieved successfully"),
                                    @ApiResponse(responseCode = "404", description = "Account not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/accounts",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getAllAccounts",
                            summary = "Get all accounts",
                            description = "Retrieve a list of all accounts in the system.",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "List of accounts retrieved successfully"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> accountFunctionRoute() {
        return RouterFunctions.route()
                .GET(pathBase + "/accounts", this::getAllAccounts)
                .POST(pathBase + "/accounts/number", this::getAccountByNumber)
                .POST(pathBase + "/accounts", this::createAccount)
                .PUT(pathBase + "/accounts", this::updateAccount)
                .PUT(pathBase + "/accounts/status", this::deleteAccount)
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
}
