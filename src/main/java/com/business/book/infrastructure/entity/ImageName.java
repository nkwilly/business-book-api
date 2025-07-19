package com.business.book.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table("image_name")
@NoArgsConstructor
@AllArgsConstructor
public class ImageName {
    @Id
    @Column("image_id")
    @JsonProperty("image_id")
    private UUID imageId;

    @Column("organization_id")
    @JsonProperty("organization_id")
    private UUID organizationId;

    @Column("name")
    @JsonProperty("name")
    private String name;
}