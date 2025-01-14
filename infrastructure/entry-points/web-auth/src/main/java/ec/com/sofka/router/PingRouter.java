package ec.com.sofka.router;

import ec.com.sofka.handler.PingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
