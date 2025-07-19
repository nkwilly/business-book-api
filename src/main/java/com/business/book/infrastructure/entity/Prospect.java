package com.business.book.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;

import java.util.UUID;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prospect {
    @Id
    @Column("prospect_id")
    @JsonProperty("prospect_id")
    private UUID prospectId;

    @Column("first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column("last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Column("email")
    @JsonProperty("email")
    private String email;

    @Column("phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Column("organization_id")
    @JsonProperty("organization_id")
    private UUID organizationId;

    @Column("created_at")
    @JsonProperty("created_at")
    private Date createdAt;

    @Column("updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;

    @Column("status")
    @JsonProperty("status")
    private String status;
}