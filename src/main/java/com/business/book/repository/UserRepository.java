package com.business.book.repository;

import com.business.book.entity.User;
import org.springframework.data.cassandra.repository.CassandraRepository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CassandraRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByTel(String tel);
}