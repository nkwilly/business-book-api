package com.business.book.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@ToString
@Table("review_visitor")
public class ReviewVisitor {

    @PrimaryKey
    private UUID id;

    @Column("review_id")
    private UUID reviewId;

    @Column("visitor_id")
    private UUID visitorId;
}
