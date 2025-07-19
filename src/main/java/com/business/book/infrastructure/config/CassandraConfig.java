package com.business.book.infrastructure.config;

import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SessionBuilderConfigurer;

import java.time.Duration;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.cassandra.port}")
    private int port;

    @Value("${spring.cassandra.local-datacenter}")
    private String localDatacenter;

    @Value("${spring.cassandra.username}")
    private String username;

    @Value("${spring.cassandra.password}")
    private String password;

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;

    @NotNull
    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    protected String getLocalDataCenter() {
        return localDatacenter;
    }

    @NotNull
    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Bean
    @Override
    protected SessionBuilderConfigurer getSessionBuilderConfigurer() {
        ProgrammaticDriverConfigLoaderBuilder configLoaderBuilder = DriverConfigLoader.programmaticBuilder()
                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(60))
                .withDuration(DefaultDriverOption.CONTROL_CONNECTION_TIMEOUT, Duration.ofSeconds(60))
                .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, Duration.ofSeconds(60))
                .withBoolean(DefaultDriverOption.METADATA_SCHEMA_ENABLED, false)
                .withBoolean(DefaultDriverOption.METADATA_TOKEN_MAP_ENABLED, false)
                .withString(DefaultDriverOption.REQUEST_CONSISTENCY, "LOCAL_ONE");

        return sessionBuilder -> sessionBuilder
                .withConfigLoader(configLoaderBuilder.build())
                .withAuthCredentials(username, password);
    }
}