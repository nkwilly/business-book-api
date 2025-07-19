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

@Builder
@Data
@Table("activity")
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @Column("activity_id")
    @JsonProperty("activity_id")
    private UUID activityId;

    @Column("organization_id")
    @JsonProperty("organization_id")
    private UUID organizationId;

    @Column("type")
    @JsonProperty("type")
    private String type;

    @Column("name")
    @JsonProperty("name")
    private String name;

    @Column("rate")
    @JsonProperty("rate")
    private int rate;

    @Column("description")
    @JsonProperty("description")
    private String description;

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
