package ink.yowyob.business.book.infrastructure.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SessionBuilderConfigurer;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.time.Duration;

@Slf4j
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.cassandra.keyspace-name}")
    private String keySpace;

    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.cassandra.local-datacenter}")
    private String datacenter;

    @Value("${spring.cassandra.port}")
    private int port;

    @Value("${spring.cassandra.username}")
    private String username;


    @Value("${spring.cassandra.password}")
    private String password;

    @Bean
    public boolean checkCassandraConnection(CqlSession session) {
        try {
            session.execute("SELECT now() FROM system.local");
            return true;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to connect to Cassandra", e);
        }
    }

    @Bean
    @Override
    public CqlSessionFactoryBean cassandraSession() {
        log.info("Creating CqlSession with contact points: {}, port: {}, datacenter: {}, keyspace: {}", contactPoints, port, datacenter, keySpace);

        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setUsername(username);
        session.setPassword(password);
        session.setContactPoints(contactPoints);
        session.setPort(port);
        session.setLocalDatacenter(datacenter);
        session.setKeyspaceName(keySpace);
        return session;
    }

    @Bean
    public CassandraTemplate cassandraTemplate(CqlSession session) {
        log.info("Creating CassandraTemplate with session: {}", session);
        return new CassandraTemplate(session);
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getLocalDataCenter() {
        return datacenter;
    }

    @Override
    protected SessionBuilderConfigurer getSessionBuilderConfigurer() {
        return new SessionBuilderConfigurer() {
            @Override
            public CqlSessionBuilder configure(CqlSessionBuilder builder) {
                log.info("Configuring CqlSession Builder");
                return builder
                        .withConfigLoader(DriverConfigLoader.programmaticBuilder()
                                .withDuration(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, Duration.ofMillis(120000))
                                .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, Duration.ofMillis(120000))
                                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(3000))
                                .build());
            }

            ;
        };
    }
}