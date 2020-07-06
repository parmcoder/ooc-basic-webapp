package ooc.webapp.possawat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
public class ParmwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParmwebApplication.class, args);
    }
    
}
