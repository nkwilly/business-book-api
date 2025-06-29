package com.business.book.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@ToString @Builder
@Table("reviews")
public class Review {

    @PrimaryKey
    private UUID id;

    private UUID organizationId;

    private String content;
}
