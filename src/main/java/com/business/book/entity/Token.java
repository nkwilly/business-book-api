package com.business.book.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("token")
@Data @ToString
public class Token {
    @PrimaryKey
    private TokeKey key;
    private UUID id;

    private UUID userId;

    private String token;

    private String roles;

    private Instant saveAt;

    @Data
    @PrimaryKeyClass
    public static class TokeKey {
        @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private UUID id;

        @PrimaryKeyColumn(name = "userId", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        private UUID userId;
    }
}