package com.business.book.infrastructure.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("tokens")
@Data @ToString @Builder
public class Token {
    @PrimaryKey
    private UUID id;

    private UUID userId;

    private String content;

    private String roles;

    private Instant saveAt;

    private Instant expireAt;

}