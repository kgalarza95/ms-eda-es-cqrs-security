package ec.com.sofka.router;


import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.dto.AccountResponseDTO;
import ec.com.sofka.handlers.AccountHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AccountRouterTest {

    @Mock
    private AccountHandler accountHandler;

    @InjectMocks
    private AccountRouter accountRouter;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToRouterFunction(accountRouter.accountFunctionRoute()).build();
    }

    @Test
    @DisplayName("Should create account")
    void createAccount() {
        AccountResponseDTO accountDTOResponse = new AccountResponseDTO("customerId", "accountId1", "Account Name", "12345", BigDecimal.valueOf(100), "ACTIVE");

        when(accountHandler.createAccount(any(AccountRequestDTO.class))).thenReturn(Mono.just(accountDTOResponse));

        webTestClient.post()
                .uri("/v1/api/accounts")
                .bodyValue(accountDTOResponse)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponseDTO.class)
                .consumeWith(response -> {
                    AccountResponseDTO actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(accountDTOResponse.getAccountName(), actualResponse.getAccountName());
                    assertEquals(accountDTOResponse.getBalance(), actualResponse.getBalance());
                });

        verify(accountHandler, times(1)).createAccount(any(AccountRequestDTO.class));
    }

    @Test
    @DisplayName("Should update account")
    void updateAccount() {
        AccountRequestDTO accountDTORequest = new AccountRequestDTO("customerId", "Updated Account Name", "12345", BigDecimal.valueOf(200));
        AccountResponseDTO accountDTOResponse = new AccountResponseDTO("customerId", "accountId1", "Updated Account Name", "12345", BigDecimal.valueOf(200), "ACTIVE");

        when(accountHandler.updateAccount(any(AccountRequestDTO.class))).thenReturn(Mono.just(accountDTOResponse));

        webTestClient.put()
                .uri("/v1/api/accounts")
                .bodyValue(accountDTORequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponseDTO.class)
                .consumeWith(response -> {
                    AccountResponseDTO actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(accountDTOResponse.getAccountName(), actualResponse.getAccountName());
                    assertEquals(accountDTOResponse.getBalance(), actualResponse.getBalance());
                });

        verify(accountHandler, times(1)).updateAccount(any(AccountRequestDTO.class));
    }

    @Test
    @DisplayName("Should retrieve all accounts")
    void getAllAccounts() {
        AccountResponseDTO accountDTO = new AccountResponseDTO("customerId1", "accountId1", "Account 1", "12345", BigDecimal.valueOf(100), "ACTIVE");
        AccountResponseDTO accountDTO2 = new AccountResponseDTO("customerId2", "accountId2", "Account 2", "12346", BigDecimal.valueOf(200), "ACTIVE");

        when(accountHandler.getAllAccounts()).thenReturn(Flux.just(accountDTO, accountDTO2));

        webTestClient.get()
                .uri("/v1/api/accounts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AccountResponseDTO.class)  // Asegúrate de esperar AccountResponseDTO en el cuerpo
                .hasSize(2)
                .consumeWith(response -> {
                    List<AccountResponseDTO> actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(accountDTO.getAccountName(), actualResponse.get(0).getAccountName());
                    assertEquals(accountDTO2.getAccountName(), actualResponse.get(1).getAccountName());
                });

        verify(accountHandler, times(1)).getAllAccounts();
    }


    @Test
    @DisplayName("Should retrieve account by number")
    void getAccountByNumber() {
        AccountRequestDTO accountDTORequest = new AccountRequestDTO("customerId", "Account Name", "12345", BigDecimal.valueOf(100));
        AccountResponseDTO accountDTOResponse = new AccountResponseDTO("customerId", "accountId1", "Account Name", "12345", BigDecimal.valueOf(100), "ACTIVE");

        when(accountHandler.getAccountByNumber(any(AccountRequestDTO.class))).thenReturn(Mono.just(accountDTOResponse));

        webTestClient.post()
                .uri("/v1/api/accounts/number")
                .bodyValue(accountDTORequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponseDTO.class)
                .consumeWith(response -> {
                    AccountResponseDTO actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(accountDTOResponse.getAccountName(), actualResponse.getAccountName());
                    assertEquals(accountDTOResponse.getBalance(), actualResponse.getBalance());
                });

        verify(accountHandler, times(1)).getAccountByNumber(any(AccountRequestDTO.class));
    }


}
