package ec.com.sofka.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( basePackages = "ec.com.sofka",
includeFilters = {
        @ComponentScan.Filter(type= FilterType.REGEX, pattern = "^.+UseCase$")
})
public class UseCaseConfig {


}
