package ec.com.sofka.configuracion;

import ec.com.sofka.exception.JwtExceptionHandlingFilter;
import ec.com.sofka.jwt.JwtAutFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(
            ServerHttpSecurity http,
            JwtAutFilter jwtAuthFilter,
            ReactiveAuthenticationManager authManager,
            CorsConfigurationSource corsConfigurationSource) {

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers("/v1/api/ping/**",
                                        "v1/api/auth/**",
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/webjars/**").permitAll()
                                .pathMatchers("/v1/api/accounts/**").hasAuthority("ADMIN")
                                .anyExchange()
                                .authenticated())
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authenticationManager(authManager)
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterBefore(jwtExceptionHandlingFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    public JwtExceptionHandlingFilter jwtExceptionHandlingFilter() {
        return new JwtExceptionHandlingFilter();
    }

}
