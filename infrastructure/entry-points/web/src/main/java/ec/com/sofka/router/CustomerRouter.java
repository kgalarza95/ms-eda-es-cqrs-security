package ec.com.sofka.router;


import ec.com.sofka.dto.AccountRequestDTO;
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

    @RouterOperations({
            @RouterOperation(
                    path = "/v1/api/customers",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "saveCustomer",
                            summary = "Create a new customer",
                            description = "This endpoint allows you to create a new customer by providing the necessary details.",
                            requestBody = @RequestBody(
                                    description = "Customer creation details",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = CustomerRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Customer created successfully",
                                            content = @Content(schema = @Schema(implementation = CustomerRequestDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/customers",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            operationId = "updateCustomer",
                            summary = "Update an existing customer",
                            description = "This endpoint allows you to update an existing customer's details.",
                            requestBody = @RequestBody(
                                    description = "Customer update details",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = CustomerRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
                                    @ApiResponse(responseCode = "404", description = "Customer not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/customers/{id}",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getCustomerById",
                            summary = "Get customer by ID",
                            description = "Retrieve the details of a customer by their unique ID.",
                            parameters = {
                                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of the customer", required = true)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Customer details retrieved successfully",
                                            content = @Content(schema = @Schema(implementation = CustomerRequestDTO.class))),
                                    @ApiResponse(responseCode = "404", description = "Customer not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/customers",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getAllCustomers",
                            summary = "Get all customers",
                            description = "Retrieve a list of all customers in the system.",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/customers/{id}",
                    method = RequestMethod.DELETE,
                    operation = @Operation(
                            operationId = "deleteCustomer",
                            summary = "Delete a customer by ID",
                            description = "Delete a customer by providing their unique ID.",
                            parameters = {
                                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of the customer", required = true)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
                                    @ApiResponse(responseCode = "404", description = "Customer not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .GET("/v1/api/customers", this::getAllCustomers)
                .POST("/v1/api/customers/byid", this::getCustomerById)
                .POST("/v1/api/customers", this::saveCustomer)
                .PUT("/v1/api/customers", this::updateCustomer)
                .PUT("/v1/api/customers/byid", this::deleteCustomer)
                .build();
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::createCustomer)
                .flatMap(customerOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customerOutDTO));
    }
    public Mono<ServerResponse> updateCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::updateCustomer)
                .flatMap(customerOutDTO -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customerOutDTO));
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::deleteCustomer)
                .flatMap(customerOutDTO -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customerOutDTO));
    }

    public Mono<ServerResponse> getAllCustomers(ServerRequest request) {
        return customerHandler.getAllCustomer()
                .collectList()
                .flatMap(customers -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customers));
    }

    private Mono<ServerResponse> getCustomerById(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerRequestDTO -> customerHandler.getCustomerById(customerRequestDTO.getAggregateId()))
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> getCustomerById_(ServerRequest request) {
        String id = request.pathVariable("id");
        return customerHandler.getCustomerById(id)
                .flatMap(customerOutDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customerOutDTO))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
