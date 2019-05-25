package net.yitun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackages = { "net.yitun.**" })
@MapperScan(basePackages = { "net.yitun.repository", "net.yitun.**.repository" }, annotationClass = Repository.class)
public class Application {

    public static void main(String[] args) {
        log.info("{} startup ...", Application.class.getName());
        SpringApplication.run(Application.class, args);
    }

}
