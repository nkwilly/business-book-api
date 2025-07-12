package ink.yowyob.business.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableCassandraRepositories(basePackages = "ink.yowyob.business.book.infrastructure.repository")
@SpringBootApplication
@EnableScheduling

public class BusinessBookApplication {
    public static void main(String[] args) {
        System.out.println("DB_URL: " + System.getenv("DB_USERNAME"));
        SpringApplication.run(BusinessBookApplication.class, args);
    }
}
