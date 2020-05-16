package by.epam.web.config;

import by.epam.web.controller.Controller;
import by.epam.web.controller.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

@Configuration
@PropertySource("classpath:datasource.properties")
@ComponentScan("by.epam.web")
@Import({CommandProviderConfig.class, ServiceConfig.class})
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

    private ServiceConfig serviceConfig;

    public WebConfig(ServiceConfig serviceConfig) {

        System.out.println(serviceConfig);
        this.serviceConfig = serviceConfig;
    }

    @Bean
    public MainController mainController() {
        return new MainController(serviceConfig.userService(), serviceConfig.tarifService());
    }
    @Bean
    public Controller controller(){
        return new Controller(serviceConfig);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/res/**").addResourceLocations("/res/");
//    }

    @Bean
    ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

}
