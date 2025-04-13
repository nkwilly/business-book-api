package com.business.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@EnableCassandraRepositories(basePackages = "com.business.book.repository")
@SpringBootApplication
public class BusinessBookApplication {

    public static void main(String[] args) {
        System.out.println("DB_URL: " + System.getenv("DB_USERNAME"));
        SpringApplication.run(BusinessBookApplication.class, args);
    }

}
