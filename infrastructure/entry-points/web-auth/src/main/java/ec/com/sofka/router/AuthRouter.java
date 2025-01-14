package ec.com.sofka.router;

import ec.com.sofka.dto.JwtRequestDTO;
import ec.com.sofka.dto.UserSystemRequestDTO;
import ec.com.sofka.handler.AdminUserHandler;
import ec.com.sofka.handler.AuthenticateHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RouterFunctions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;


@Configuration
public class AuthRouter {

    private final AdminUserHandler authHandler;
    private final AuthenticateHandler authenticateHandler;
    private final String pathBase = "/v1/api";

    public AuthRouter(AdminUserHandler authHandler, AuthenticateHandler authenticateHandler) {
        this.authHandler = authHandler;
        this.authenticateHandler = authenticateHandler;
    }

    @RouterOperations({
            @RouterOperation(
                    path = "/v1/api/getAccountByNumber",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "getAccountByNumber",
                            summary = "Get account by email",
                            description = "Retrieve account details using an email address.",
                            requestBody = @RequestBody(
                                    description = "Details of the email to search for",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = UserSystemRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Account details retrieved successfully"),
                                    @ApiResponse(responseCode = "404", description = "Account not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/create",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "createUserSystem",
                            summary = "Create a new user system",
                            description = "Create a new user system with the provided details.",
                            requestBody = @RequestBody(
                                    description = "Details for creating the user system",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = UserSystemRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "User system created successfully"),
                                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> authFunctionRoute() {
        return RouterFunctions.route()
                .POST(pathBase + "/auth/usersadmin/authenticate", this::authenticate)
                .POST(pathBase + "/auth/usersadmin/byuser", this::getByEmail)
                .POST(pathBase + "/auth/usersadmin", this::createUserSystem)
                .build();
    }

    private Mono<ServerResponse> getByEmail(ServerRequest request) {
        return request.bodyToMono(UserSystemRequestDTO.class)
                .flatMap(authHandler::getByEmail)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    private Mono<ServerResponse> createUserSystem(ServerRequest request) {
        return request.bodyToMono(UserSystemRequestDTO.class)
                .flatMap(authHandler::createUserSystem)
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    private Mono<ServerResponse> authenticate(ServerRequest request) {
        return request.bodyToMono(JwtRequestDTO.class)
                .flatMap(authenticateHandler::authenticate)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }


}
