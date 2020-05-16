package by.epam.web.config;

import by.epam.web.command.CommandProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandProviderConfig {
    @Bean
    public CommandProvider commandProvider(){
        return new CommandProvider();
    }
}
