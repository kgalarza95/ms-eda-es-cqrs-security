package ec.com.sofka.router;

import ec.com.sofka.handler.PingHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class PingRouter {

    private final PingHandler pingHandler;
    private final String pathBase = "/v1/api";

    public PingRouter(PingHandler pingHandler) {
        this.pingHandler = pingHandler;
    }

    @RouterOperations({
            @RouterOperation(
                    path = "/v1/api/ping",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "ping",
                            summary = "Ping the API",
                            description = "This endpoint checks the health of the API and returns the current server time.",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Ping successful, current server time"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> pingRouters(){
        return RouterFunctions
       .route(GET(pathBase + "/ping").and(accept(APPLICATION_JSON)), this::ping);
    }

    public Mono<ServerResponse> ping(ServerRequest request) {
        return pingHandler.ping()
                .flatMap(localDateTime ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(localDateTime)
                );
    }

}
