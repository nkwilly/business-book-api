package com.business.book.infrastructure.entity;

import com.business.book.infrastructure.entity.constants.DomainName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;

import java.util.UUID;

@Data @ToString @EqualsAndHashCode
@AllArgsConstructor
public class Domain {
    @Column("id")
    private UUID id;

    @Column("domain_name")
    private DomainName domainName;

    @Column("description")
    private String description;
}