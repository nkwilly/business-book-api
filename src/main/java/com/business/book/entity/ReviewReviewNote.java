package com.business.book.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@ToString
@Table("review_review_note")
public class ReviewReviewNote {

    @PrimaryKey
    private UUID id;

    @Column("review_id")
    private UUID reviewId;

    @Column("review_note_id")
    private UUID reviewNoteId;
}
