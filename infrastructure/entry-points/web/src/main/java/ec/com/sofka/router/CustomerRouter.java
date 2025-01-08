package ec.com.sofka.router;


import ec.com.sofka.dto.CustomerRequestDTO;
import ec.com.sofka.handlers.CustomerHandler;
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
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class CustomerRouter {

    private final CustomerHandler customerHandler;

    public CustomerRouter(CustomerHandler customerHandler) {
        this.customerHandler = customerHandler;
    }


    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .POST("/v1/api/customers", this::saveCustomer)
                .PUT("/v1/api/customers", this::saveCustomer)
//                .GET("/v1/api/customers/{id}", this::getCustomerById)
//                .GET("/v1/api/customers", this::getAllCustomers)
//                .DELETE("/v1/api/customers/{id}", this::deleteCustomer)
                .build();
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::createCustomer)
                .flatMap(customerOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customerOutDTO));
    }

//    public Mono<ServerResponse> getCustomerById(ServerRequest request) {
//        String id = request.pathVariable("id");
//        return customerHandler.findCustomerById(id)
//                .flatMap(customerOutDTO -> ServerResponse.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .bodyValue(customerOutDTO))
//                .switchIfEmpty(ServerResponse.notFound().build());
//    }
//
//    public Mono<ServerResponse> getAllCustomers(ServerRequest request) {
//        return customerHandler.findAllCustomers()
//                .collectList()
//                .flatMap(customers -> ServerResponse.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .bodyValue(customers));
//    }
//
//    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
//        String id = request.pathVariable("id");
//        return customerHandler.deleteCustomerById(id)
//                .then(ServerResponse.noContent().build());
//    }
}
