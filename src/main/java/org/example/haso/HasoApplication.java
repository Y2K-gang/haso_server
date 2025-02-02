package org.example.haso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HasoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HasoApplication.class, args);
    }


}
