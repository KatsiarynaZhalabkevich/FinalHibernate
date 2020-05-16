package by.epam.web.config;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigTest {
    @Test
    public void dao(){
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DaoConfig.class);
        context.refresh();
        for(String s: context.getBeanDefinitionNames()){
            System.out.println(s);
        }
    }
    @Test
    public void service(){
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ServiceConfig.class);
        context.refresh();
        for(String s: context.getBeanDefinitionNames()){
            System.out.println(s);
        }
    }
    @Test
    public void setting(){
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(WebConfig.class);
        context.refresh();
        for(String s: context.getBeanDefinitionNames()){
            System.out.println(s);
        }
    }
    @Test
    public void hibernate(){
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(HibernateConfig.class);
        context.refresh();
        for(String s: context.getBeanDefinitionNames()){
            System.out.println(s);
        }
    }

}
