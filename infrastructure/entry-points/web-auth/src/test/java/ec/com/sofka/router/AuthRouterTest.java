package ec.com.sofka.router;

import ec.com.sofka.commands.responses.usersystem.CreateUserAdminResponse;
import ec.com.sofka.dto.JwtRequestDTO;
import ec.com.sofka.dto.JwtResponseDTO;
import ec.com.sofka.dto.UserSystemRequestDTO;
import ec.com.sofka.handler.AdminUserHandler;
import ec.com.sofka.handler.AuthenticateHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AuthRouterTest {

    @Mock
    private AdminUserHandler authHandler;

    @Mock
    private AuthenticateHandler authenticateHandler;

    @InjectMocks
    private AuthRouter authRouter;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToRouterFunction(authRouter.authFunctionRoute()).build();
    }

    @Test
    @DisplayName("Should authenticate user successfully")
    void authenticateUserSuccessfully() {
        JwtRequestDTO jwtRequestDTO = new JwtRequestDTO("testuser@example.com", "password123");
        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO("sample-jwt-token");

        when(authenticateHandler.authenticate(any(JwtRequestDTO.class)))
                .thenReturn(Mono.just(jwtResponseDTO));

        webTestClient.post()
                .uri("/v1/api/auth/usersadmin/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jwtRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtResponseDTO.class)
                .consumeWith(response -> {
                    JwtResponseDTO actualResponse = response.getResponseBody();
                    assertNotNull(actualResponse);
                    assertEquals(jwtResponseDTO.getToken(), actualResponse.getToken());
                });

        verify(authenticateHandler, times(1)).authenticate(any(JwtRequestDTO.class));
    }


    @Test
    @DisplayName("Should create user system successfully")
    void createUserSystemSuccessfully() {


            UserSystemRequestDTO userSystemRequestDTO = new UserSystemRequestDTO(
                "New", "User", "newuser@example.com", "password123", "ADMIN"
        );
        CreateUserAdminResponse createUserAdminResponse = new CreateUserAdminResponse(
                "2", "New", "User", "newuser@example.com", "password123", "ADMIN"
        );

        when(authHandler.createUserSystem(any(UserSystemRequestDTO.class)))
                .thenReturn(Mono.just(createUserAdminResponse));

        // Act
        webTestClient.post()
                .uri("/v1/api/auth/usersadmin")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userSystemRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateUserAdminResponse.class)
                .consumeWith(response -> {
                    CreateUserAdminResponse actualResponse = response.getResponseBody();
                    assertNotNull(actualResponse);
                    assertEquals(createUserAdminResponse.getEmail(), actualResponse.getEmail());
                    assertEquals(createUserAdminResponse.getFirstname(), actualResponse.getFirstname());
                    assertEquals(createUserAdminResponse.getLastname(), actualResponse.getLastname());
                    assertEquals(createUserAdminResponse.getRole(), actualResponse.getRole());
                });

        verify(authHandler, times(1)).createUserSystem(any(UserSystemRequestDTO.class));
    }


}
