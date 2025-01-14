package ec.com.sofka.handler;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class PingHandler {

    public Mono<LocalDateTime> ping(){return Mono.just(LocalDateTime.now());}
}
