package ec.com.sofka.handler;

import ec.com.sofka.dto.JwtRequestDTO;
import ec.com.sofka.dto.JwtResponseDTO;
import ec.com.sofka.jwt.JwtService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthenticateHandler {

    private final JwtService jwtService;
    private final ReactiveAuthenticationManager authManager;

    public AuthenticateHandler(JwtService jwtService, ReactiveAuthenticationManager authManager) {
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public Mono<JwtResponseDTO> authenticate(JwtRequestDTO request) {
        System.out.println("Attempting to authenticate user: " + request.getEmail());
        System.out.println("Received password: " + request.getPassword());

        return authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()))
                    .doOnError(e -> System.err.println("Error during authentication: "+ e))
                    .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                    .map(auth -> {
                        UserDetails userDetails = (UserDetails) auth.getPrincipal();
                        System.out.println("Authentication successful for user: " + userDetails.getUsername());
                        return getAuthResponse(userDetails);
                    });

    }

    private JwtResponseDTO getAuthResponse(UserDetails userDetails) {
        var extraClaims = extractAuthorities("roles", userDetails);

        var jwtToken = jwtService.generateToken(userDetails, extraClaims);
        return new JwtResponseDTO(jwtToken);
    }

    private Map<String, Object> extractAuthorities(String key, UserDetails userDetails) {
        Map<String, Object> authorities = new HashMap<>();

        authorities.put(key,
                userDetails
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")));

        return authorities;
    }

}
