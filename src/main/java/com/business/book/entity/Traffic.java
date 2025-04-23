package com.business.book.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@ToString
@Table("traffics")
public class Traffic {

    @PrimaryKey
    private UUID enterpriseId;

    private int viewsCount;
    private int contactClicks;
    private int subscriptions;
}
