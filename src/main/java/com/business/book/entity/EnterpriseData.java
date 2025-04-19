package com.business.book.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data @Table("enterprise_data")
public class EnterpriseData {
    @PrimaryKey
    private UUID id;

    private UUID enterpriseId;

    private Long viewsNumbers;
}