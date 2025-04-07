package de.unistuttgart.umlgamebackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UmlGameServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmlGameServiceApplication.class, args);
    }
}
