package com.erp.shardingjdbcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactorySharding",
        transactionManagerRef = "transactionManagerSharding", basePackages = "com.erp.shardingjdbcdemo")
//@EntityScan(basePackages = "com.erp.shardingjdbcdemo.domain")
@SpringBootApplication
public class ShardingjdbcdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingjdbcdemoApplication.class, args);
    }

}
