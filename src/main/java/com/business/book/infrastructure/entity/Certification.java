package com.business.book.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Table("certifications")
@NoArgsConstructor
@AllArgsConstructor
public class Certification {
    @Id
    @Column("certification_id")
    @JsonProperty("certification_id")
    private UUID certificationId;

    @Column("organization_id")
    @JsonProperty("organization_id")
    private UUID organizationId;

    @Column("type")
    @JsonProperty("type")
    private String type;

    @Column("name")
    @JsonProperty("name")
    private String name;

    @Column("description")
    @JsonProperty("description")
    private String description;

    @Column("obtainement_date")
    @JsonProperty("obtainement_date")
    private Date obtainementDate;

    @Column("created_at")
    @JsonProperty("created_at")
    private Date createdAt;

    @Column("updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;

    @Column("created_by")
    @JsonProperty("created_by")
    private String createdBy;

    @Column("deleted_at")
    @JsonProperty("deleted_at")
    private Date deletedAt;

    @Column("updated_by")
    @JsonProperty("updated_by")
    private String updatedBy;
}