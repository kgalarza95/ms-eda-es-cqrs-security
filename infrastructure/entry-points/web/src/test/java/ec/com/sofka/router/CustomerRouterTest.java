package ec.com.sofka.router;

import ec.com.sofka.dto.CustomerRequestDTO;
import ec.com.sofka.dto.CustomerResponseDTO;
import ec.com.sofka.handlers.CustomerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToRouterFunction;

public class CustomerRouterTest {
    @Mock
    private CustomerHandler customerHandler;

    @InjectMocks
    private CustomerRouter customerRouter;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = bindToRouterFunction(customerRouter.route()).build();
    }

    @Test
    @DisplayName("Should create customer")
    void createCustomer() {
        CustomerRequestDTO customerDTORequest = new CustomerRequestDTO("1", "12345", "John", "Doe", "john.doe@example.com", "1234567890", "Some address", LocalDate.of(1990, 1, 1));
        CustomerResponseDTO customerDTOResponse = new CustomerResponseDTO("1", "12345", "John", "Doe", "john.doe@example.com", "1234567890", "Some address", LocalDate.of(1990, 1, 1));


        when(customerHandler.createCustomer(any(CustomerRequestDTO.class))).thenReturn(Mono.just(customerDTOResponse));

        webTestClient.post()
                .uri("/v1/api/customers")
                .bodyValue(customerDTORequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerResponseDTO.class)
                .consumeWith(response -> {
                    CustomerResponseDTO actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(customerDTOResponse.getFirstName(), actualResponse.getFirstName());
                    assertEquals(customerDTOResponse.getLastName(), actualResponse.getLastName());
                });

        verify(customerHandler, times(1)).createCustomer(any(CustomerRequestDTO.class));
    }

    @Test
    @DisplayName("Should update customer")
    void updateCustomer() {
        CustomerRequestDTO customerDTORequest = new CustomerRequestDTO("1", "12345", "Jane", "Doe", "jane.doe@example.com", "0987654321", "Another address", LocalDate.of(1991, 2, 2));
        CustomerResponseDTO customerDTOResponse = new CustomerResponseDTO("1", "12345", "Jane", "Doe", "jane.doe@example.com", "0987654321", "Another address", LocalDate.of(1991, 2, 2));

        when(customerHandler.updateCustomer(any(CustomerRequestDTO.class))).thenReturn(Mono.just(customerDTOResponse));

        webTestClient.put()
                .uri("/v1/api/customers")
                .bodyValue(customerDTORequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponseDTO.class)
                .consumeWith(response -> {
                    CustomerResponseDTO actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(customerDTOResponse.getFirstName(), actualResponse.getFirstName());
                    assertEquals(customerDTOResponse.getLastName(), actualResponse.getLastName());
                });

        verify(customerHandler, times(1)).updateCustomer(any(CustomerRequestDTO.class));
    }

    @Test
    @DisplayName("Should retrieve all customers")
    void getAllCustomers() {
        CustomerResponseDTO customerDTO1 = new CustomerResponseDTO("1", "12345", "John", "Doe", "john.doe@example.com", "1234567890", "Some address", LocalDate.of(1990, 1, 1));
        CustomerResponseDTO customerDTO2 = new CustomerResponseDTO("2", "67890", "Jane", "Doe", "jane.doe@example.com", "0987654321", "Another address", LocalDate.of(1991, 2, 2));

        when(customerHandler.getAllCustomer()).thenReturn(Flux.just(customerDTO1, customerDTO2));

        webTestClient.get()
                .uri("/v1/api/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerResponseDTO.class)
                .hasSize(2)
                .consumeWith(response -> {
                    List<CustomerResponseDTO> actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(customerDTO1.getFirstName(), actualResponse.get(0).getFirstName());
                    assertEquals(customerDTO2.getFirstName(), actualResponse.get(1).getFirstName());
                });

        verify(customerHandler, times(1)).getAllCustomer();
    }

    @Test
    @DisplayName("Should retrieve customer by ID")
    void getCustomerById() {
        CustomerRequestDTO customerDTORequest = new CustomerRequestDTO("1", "12345", "John", "Doe", "john.doe@example.com", "1234567890", "Some address", LocalDate.of(1990, 1, 1));
        CustomerResponseDTO customerDTOResponse = new CustomerResponseDTO("1", "12345", "John", "Doe", "john.doe@example.com", "1234567890", "Some address", LocalDate.of(1990, 1, 1));

        when(customerHandler.getCustomerById(anyString())).thenReturn(Mono.just(customerDTOResponse));

        webTestClient.post()
                .uri("/v1/api/customers/byid")
                .bodyValue(customerDTORequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponseDTO.class)
                .consumeWith(response -> {
                    CustomerResponseDTO actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(customerDTOResponse.getFirstName(), actualResponse.getFirstName());
                    assertEquals(customerDTOResponse.getLastName(), actualResponse.getLastName());
                });

        verify(customerHandler, times(1)).getCustomerById(anyString());
    }

    @Test
    @DisplayName("Should delete customer")
    void deleteCustomer() {
        CustomerRequestDTO customerDTORequest = new CustomerRequestDTO("1", "12345", "John", "Doe", "john.doe@example.com", "1234567890", "Some address", LocalDate.of(1990, 1, 1));
        CustomerResponseDTO customerDTOResponse = new CustomerResponseDTO("1", "12345", "John", "Doe", "john.doe@example.com", "1234567890", "Some address", LocalDate.of(1990, 1, 1));

        when(customerHandler.deleteCustomer(any(CustomerRequestDTO.class))).thenReturn(Mono.just(customerDTOResponse));

        webTestClient.put()
                .uri("/v1/api/customers/byid")
                .bodyValue(customerDTORequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponseDTO.class)
                .consumeWith(response -> {
                    CustomerResponseDTO actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(customerDTOResponse.getFirstName(), actualResponse.getFirstName());
                    assertEquals(customerDTOResponse.getLastName(), actualResponse.getLastName());
                });

        verify(customerHandler, times(1)).deleteCustomer(any(CustomerRequestDTO.class));
    }
}
