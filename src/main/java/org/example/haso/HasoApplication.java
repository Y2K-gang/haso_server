package org.example.haso;

import org.example.haso.global.config.AwsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
//@ComponentScan(basePackages = "org.example.haso.global.config")  // AwsConfig 클래스가 위치한 패키지를 명시
//@ComponentScan(basePackages = "org.example.haso.member")
public class HasoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HasoApplication.class, args);
    }


}


