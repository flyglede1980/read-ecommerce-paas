package net.yitun.cloud.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class GatewayApplication {

    @Value("${spring.application.name}")
    private String name;

    @GetMapping(value = "/tt")
    public String home() {
        return String.valueOf(System.currentTimeMillis());
    }

    @GetMapping(value = "/node")
    public String node() {
        return this.name;
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}