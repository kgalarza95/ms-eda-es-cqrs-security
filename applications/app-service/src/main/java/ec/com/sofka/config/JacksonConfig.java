package ec.com.sofka.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ec.com.sofka.IJSONMapper;
import ec.com.sofka.JSONMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    @Bean
    public IJSONMapper jsonMapper(){
        return new JSONMap();
    }
}
