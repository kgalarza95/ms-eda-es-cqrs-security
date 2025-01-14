package ec.com.sofka.handler;

import ec.com.sofka.commands.responses.usersystem.CreateUserAdminResponse;
import ec.com.sofka.commands.usecase.usersystem.CreateUserSystemUseCase;
import ec.com.sofka.commands.usersystem.CreateUserSystemCommand;
import ec.com.sofka.dto.UserSystemRequestDTO;
import ec.com.sofka.model.util.RoleSystemEnum;
import ec.com.sofka.queries.usecase.usersystem.GetUserSystemByEmailUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AdminUserHandler {

    private final CreateUserSystemUseCase createUserSystemUseCase;
    private final GetUserSystemByEmailUseCase getUserSystemByEmailUseCase;
    private final PasswordEncoder passwordEncoder;

    public AdminUserHandler(CreateUserSystemUseCase createUserSystemUseCase, GetUserSystemByEmailUseCase getUserSystemByEmailUseCase, PasswordEncoder passwordEncoder) {
        this.createUserSystemUseCase = createUserSystemUseCase;
        this.getUserSystemByEmailUseCase = getUserSystemByEmailUseCase;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<CreateUserAdminResponse> getByEmail(UserSystemRequestDTO request) {
        return getUserSystemByEmailUseCase.findByEmail( request.getEmail())
                .map(response -> new CreateUserAdminResponse(
                response.getId(),
                response.getFirstname(),
                response.getLastname(),
                response.getEmail(),
                response.getPassword(),
                response.getRole()
        ));
    }

    public Mono<CreateUserAdminResponse> createUserSystem(UserSystemRequestDTO request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return createUserSystemUseCase.save(
                new CreateUserSystemCommand(
                        request.getFirstname(),
                        request.getLastname(),
                        request.getEmail(),
                        encodedPassword,
                        RoleSystemEnum.valueOf(request.getRole())
                )
        ).map(response -> new CreateUserAdminResponse(
                response.getId(),
                response.getFirstname(),
                response.getLastname(),
                response.getEmail(),
                response.getPassword(),
                response.getRole()
        ));
    }

}
