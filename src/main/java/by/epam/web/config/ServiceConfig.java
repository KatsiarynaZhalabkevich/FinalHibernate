package by.epam.web.config;

import by.epam.web.service.NoteService;
import by.epam.web.service.TarifService;
import by.epam.web.service.UserService;
import by.epam.web.service.impl.NoteServiceImpl;
import by.epam.web.service.impl.TarifServiceImpl;
import by.epam.web.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DaoConfig.class)
public class ServiceConfig {
    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }
    @Bean
    public TarifService tarifService(){
        return new TarifServiceImpl();
    }
    @Bean
    public NoteService noteService(){
        return new NoteServiceImpl();
    }
}
