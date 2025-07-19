package com.business.book.infrastructure.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@ToString @Builder
@Table("review_notes")
public class ReviewNote {

    @PrimaryKey
    private UUID id;

    private UUID organizationId;

    private Integer note;
}