package com.business.book.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table("customers")
public class Customer extends User {

    @PrimaryKey
    private UUID id;

    private boolean hindered; 
}
