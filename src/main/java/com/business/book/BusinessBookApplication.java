package com.business.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableReactiveCassandraRepositories(basePackages = "com.business.book.infrastructure.repository")
@SpringBootApplication
@EnableScheduling
public class BusinessBookApplication {

    public static void main(String[] args) {

        SpringApplication.run(BusinessBookApplication.class, args);
    }
}