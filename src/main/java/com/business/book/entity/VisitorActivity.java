package com.business.book.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.business.book.entity.constants.Action;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
@Table("visitor_activity")
public class VisitorActivity {

    @PrimaryKey
    private UUID visitorId;

    private LocalDateTime activityTime;

    private Action action;

    private String details;
}
