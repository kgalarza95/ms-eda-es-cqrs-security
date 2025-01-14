package ec.com.sofka.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.web.server.WebFilter;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionHandlingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(ExpiredJwtException.class, ex -> handleJwtException(exchange, ex, "JWT token has expired.", HttpStatus.UNAUTHORIZED))
                .onErrorResume(MalformedJwtException.class, ex -> handleJwtException(exchange, ex, "Malformed JWT token.", HttpStatus.BAD_REQUEST));
    }

    private Mono<Void> handleJwtException(ServerWebExchange exchange, Exception ex, String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("details", ex.getMessage());

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                .bufferFactory().wrap(serializeResponse(response))));
    }

    private byte[] serializeResponse(Map<String, Object> response) {
        try {
            return new ObjectMapper().writeValueAsBytes(response);
        } catch (Exception e) {
            return ("{\"status\":500,\"message\":\"Internal Server Error\"}").getBytes();
        }
    }
}
