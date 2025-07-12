package yowyob.business.book;

import ink.yowyob.business.book.infrastructure.entity.User;
import ink.yowyob.business.book.infrastructure.repository.RoleRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRoleRepository;
import ink.yowyob.business.book.utils.GenerateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class CustomerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        IntStream.range(1, 11).forEach(i -> {
            User user = new User();
            user.setId(GenerateUtils.generateId());
            user.setEmail("user" + i + "@example.com");
            user.setUsername("user" + i);
            user.setTel("+23765000000" + i);
            user.setPassword("password" + i);
            user.setCreatedAt(Instant.now());
            user.setUpdatedAt(Instant.now());
            user.setCreatedBy(GenerateUtils.generateId());
            user.setLastModifiedBy(GenerateUtils.generateId());

            userRepository.save(user);
        });
    }

    @Test
    public void testCustomer() {
        //Page<User> p = (Page<User>) userRepository.findAll();
    }
}
