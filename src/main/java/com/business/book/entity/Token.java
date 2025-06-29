package com.business.book.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("tokens")
@Data @ToString
public class Token {
    @PrimaryKey
    private UUID id;

    private UUID userId;

    private String content;

    private String roles;

    private Instant saveAt;

}