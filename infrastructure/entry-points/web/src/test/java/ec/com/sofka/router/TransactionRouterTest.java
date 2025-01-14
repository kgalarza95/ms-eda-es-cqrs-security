package ec.com.sofka.router;


import ec.com.sofka.dto.CustomerRequestDTO;
import ec.com.sofka.dto.TransactionInDTO;
import ec.com.sofka.dto.TransactionOutDTO;
import ec.com.sofka.handlers.TransactionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionRouterTest {

    @Mock
    private TransactionHandler transactionHandler;

    @InjectMocks
    private TransactionRouter transactionRouter;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToRouterFunction(transactionRouter.TransactionRouterBean()).build();
    }

    @Test
    @DisplayName("Should make branch deposit successfully")
    void makeBranchDeposit() {
        // Arrange
        TransactionInDTO transactionInDTO = new TransactionInDTO("123", "Branch Deposit", BigDecimal.valueOf(1000), "C", LocalDate.now(), "accountId1");
        TransactionOutDTO transactionOutDTO = new TransactionOutDTO("1", "Branch Deposit", BigDecimal.valueOf(1000), "C", LocalDate.now(), "accountId1");

        when(transactionHandler.makeBranchDeposit(any(TransactionInDTO.class)))
                .thenReturn(Mono.just(transactionOutDTO));

        webTestClient.post()
                .uri("/v1/api/movimientos/deposito/sucursal")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionInDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionOutDTO.class)
                .consumeWith(response -> {
                    TransactionOutDTO actualResponse = response.getResponseBody();
                    assertNotNull(actualResponse, "Response body should not be null");
                    assertEquals(transactionOutDTO.getDescription(), actualResponse.getDescription());
                    assertEquals(transactionOutDTO.getAmount(), actualResponse.getAmount());
                    assertEquals(transactionOutDTO.getTransactionType(), actualResponse.getTransactionType());
                });

        verify(transactionHandler, times(1)).makeBranchDeposit(any(TransactionInDTO.class));
    }

    @Test
    @DisplayName("Should make ATM deposit successfully")
    void makeATMDeposit() {
        TransactionInDTO transactionInDTO = new TransactionInDTO("123", "ATM Deposit", BigDecimal.valueOf(1000), "C", LocalDate.now(), "accountId1");
        TransactionOutDTO transactionOutDTO = new TransactionOutDTO("2", "ATM Deposit", BigDecimal.valueOf(1000), "C", LocalDate.now(), "accountId1");

        when(transactionHandler.makeATMDeposit(any(TransactionInDTO.class)))
                .thenReturn(Mono.just(transactionOutDTO));

        webTestClient.post()
                .uri("/v1/api/movimientos/deposito/cajero")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionInDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionOutDTO.class)
                .consumeWith(response -> {
                    TransactionOutDTO actualResponse = response.getResponseBody();
                    assertNotNull(actualResponse, "Response body should not be null");
                    assertEquals(transactionOutDTO.getDescription(), actualResponse.getDescription());
                    assertEquals(transactionOutDTO.getAmount(), actualResponse.getAmount());
                    assertEquals(transactionOutDTO.getTransactionType(), actualResponse.getTransactionType());
                });

        verify(transactionHandler, times(1)).makeATMDeposit(any(TransactionInDTO.class));
    }

    @Test
    @DisplayName("Should make deposit to another account successfully")
    void makeDepositToAnotherAccount() {
        TransactionInDTO transactionInDTO = new TransactionInDTO("123", "Deposit to Another Account", BigDecimal.valueOf(1000), "C", LocalDate.now(), "accountId1");
        TransactionOutDTO transactionOutDTO = new TransactionOutDTO("3", "Deposit to Another Account", BigDecimal.valueOf(1000), "C", LocalDate.now(), "accountId1");

        when(transactionHandler.makeDepositToAnotherAccount(any(TransactionInDTO.class)))
                .thenReturn(Mono.just(transactionOutDTO));

        webTestClient.post()
                .uri("/v1/api/movimientos/deposito/otra-cuenta")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionInDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionOutDTO.class)
                .consumeWith(response -> {
                    TransactionOutDTO actualResponse = response.getResponseBody();
                    assertNotNull(actualResponse, "Response body should not be null");
                    assertEquals(transactionOutDTO.getDescription(), actualResponse.getDescription());
                    assertEquals(transactionOutDTO.getAmount(), actualResponse.getAmount());
                    assertEquals(transactionOutDTO.getTransactionType(), actualResponse.getTransactionType());
                });

        verify(transactionHandler, times(1)).makeDepositToAnotherAccount(any(TransactionInDTO.class));
    }

    @Test
    @DisplayName("Should make physical purchase successfully")
    void makePhysicalPurchase() {
        TransactionInDTO transactionInDTO = new TransactionInDTO("123", "Physical Purchase", BigDecimal.valueOf(500), "D", LocalDate.now(), "accountId1");
        TransactionOutDTO transactionOutDTO = new TransactionOutDTO("4", "Physical Purchase", BigDecimal.valueOf(500), "D", LocalDate.now(), "accountId1");

        when(transactionHandler.makePhysicalPurchase(any(TransactionInDTO.class)))
                .thenReturn(Mono.just(transactionOutDTO));

        webTestClient.post()
                .uri("/v1/api/movimientos/compra/fisica")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionInDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionOutDTO.class)
                .consumeWith(response -> {
                    TransactionOutDTO actualResponse = response.getResponseBody();
                    assertNotNull(actualResponse, "Response body should not be null");
                    assertEquals(transactionOutDTO.getDescription(), actualResponse.getDescription());
                    assertEquals(transactionOutDTO.getAmount(), actualResponse.getAmount());
                    assertEquals(transactionOutDTO.getTransactionType(), actualResponse.getTransactionType());
                });

        verify(transactionHandler, times(1)).makePhysicalPurchase(any(TransactionInDTO.class));
    }

    @Test
    @DisplayName("Should make online purchase successfully")
    void makeOnlinePurchase() {
        TransactionInDTO transactionInDTO = new TransactionInDTO("123", "Online Purchase", BigDecimal.valueOf(1500), "D", LocalDate.now(), "accountId1");
        TransactionOutDTO transactionOutDTO = new TransactionOutDTO("5", "Online Purchase", BigDecimal.valueOf(1500), "D", LocalDate.now(), "accountId1");

        when(transactionHandler.makeOnlinePurchase(any(TransactionInDTO.class)))
                .thenReturn(Mono.just(transactionOutDTO));

        webTestClient.post()
                .uri("/v1/api/movimientos/compra/web")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionInDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionOutDTO.class)
                .consumeWith(response -> {
                    TransactionOutDTO actualResponse = response.getResponseBody();
                    assertNotNull(actualResponse, "Response body should not be null");
                    assertEquals(transactionOutDTO.getDescription(), actualResponse.getDescription());
                    assertEquals(transactionOutDTO.getAmount(), actualResponse.getAmount());
                    assertEquals(transactionOutDTO.getTransactionType(), actualResponse.getTransactionType());
                });

        verify(transactionHandler, times(1)).makeOnlinePurchase(any(TransactionInDTO.class));
    }

    @Test
    @DisplayName("Should make ATM withdrawal successfully")
    void makeATMWithdrawal() {
        TransactionInDTO transactionInDTO = new TransactionInDTO("123", "ATM Withdrawal", BigDecimal.valueOf(200), "D", LocalDate.now(), "accountId1");
        TransactionOutDTO transactionOutDTO = new TransactionOutDTO("6", "ATM Withdrawal", BigDecimal.valueOf(200), "D", LocalDate.now(), "accountId1");

        when(transactionHandler.makeATMWithdrawal(any(TransactionInDTO.class)))
                .thenReturn(Mono.just(transactionOutDTO));

        webTestClient.post()
                .uri("/v1/api/movimientos/retiro/cajero")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionInDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionOutDTO.class)
                .consumeWith(response -> {
                    TransactionOutDTO actualResponse = response.getResponseBody();
                    assertNotNull(actualResponse, "Response body should not be null");
                    assertEquals(transactionOutDTO.getDescription(), actualResponse.getDescription());
                    assertEquals(transactionOutDTO.getAmount(), actualResponse.getAmount());
                    assertEquals(transactionOutDTO.getTransactionType(), actualResponse.getTransactionType());
                });

        verify(transactionHandler, times(1)).makeATMWithdrawal(any(TransactionInDTO.class));
    }
}
