package com.business.book.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("token")
@Data @ToString
public class Token {
    @PrimaryKey
    private UUID id;

    @PrimaryKey
    private UUID userId;

    private String token;

    private String roles;

    private Instant saveAt;
}