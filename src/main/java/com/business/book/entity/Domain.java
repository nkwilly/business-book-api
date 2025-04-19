package com.business.book.entity;

import com.business.book.entity.constants.DomainName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Data @ToString @EqualsAndHashCode
public class Domain {
    private UUID id;

    private DomainName domainName;

    private String description;
}