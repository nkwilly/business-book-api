package com.business.book;

import com.business.book.entity.User;
import com.business.book.repository.RoleRepository;
import com.business.book.repository.UserRepository;
import com.business.book.repository.UserRoleRepository;
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
            user.setId(UUID.randomUUID());
            user.setEmail("user" + i + "@example.com");
            user.setUsername("user" + i);
            user.setTel("+23765000000" + i);
            user.setPassword("password" + i);
            user.setCreatedAt(Instant.now());
            user.setUpdatedAt(Instant.now());
            user.setCreatedBy(UUID.randomUUID());
            user.setLastModifiedBy(UUID.randomUUID());

            userRepository.save(user);
        });
    }

    @Test
    public void testCustomer() {
        //Page<User> p = (Page<User>) userRepository.findAll();
    }
}
