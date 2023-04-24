package antifraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class AntiFraudApplication {
    public static void main(String[] args) {
        SpringApplication.run(AntiFraudApplication.class, args);
    }
}