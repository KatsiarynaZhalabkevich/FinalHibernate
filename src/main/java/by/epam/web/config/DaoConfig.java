package by.epam.web.config;

import by.epam.web.dao.NoteDAO;
import by.epam.web.dao.TarifDAO;
import by.epam.web.dao.UserDAO;
import by.epam.web.dao.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@Import(HibernateConfig.class)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "by.epam.web.repository")
public class DaoConfig {
//    @Bean
//    public UserDAO userDAO (){
//        return new HibernateUserDAO();
//    }
//    @Bean
//    public TarifDAO tarifDAO(){
//        return new HibernateTariffDAO();
//    }
//    @Bean
//    public NoteDAO noteDAO(){
//        return new HibernateNoteDAO();
//    }

    @Bean
    public UserDAO userDAO (){
        return new SpringDataUserDAO();
    }
    @Bean
    public TarifDAO tarifDAO(){
        return new SpringDataTariffDAO();
    }
    @Bean
    public NoteDAO noteDAO(){
        return new SpringDataNoteDAO();
    }
}
