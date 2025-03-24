package org.example.haso;

import org.example.haso.global.config.AwsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "org.example.haso")
@ConfigurationPropertiesScan
public class HasoApplication {
    public static void main(String[] args) {
        SpringApplication.run(HasoApplication.class, args);
    }
}
