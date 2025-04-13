package com.business.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data @Table("users")
public class User {
    @PrimaryKey
    @JsonIgnore @JsonProperty
    private UUID id;

    @JsonProperty
    @Indexed
    private String email;

    @JsonProperty
    @Indexed
    private String username;

    @JsonProperty
    @Indexed
    private String tel;

    @JsonIgnore @JsonProperty
    private String password;

    @CreatedDate @JsonIgnore
    @Column("create_at")
    private Instant createdAt;

    @LastModifiedDate @JsonIgnore
    @Column("update_at")
    private Instant updatedAt;

    @CreatedBy @JsonIgnore
    @Column("create_by")
    private UUID createdBy;

    @LastModifiedBy @JsonIgnore
    @Column("last_modified_by")
    private UUID lastModifiedBy;


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", tel='" + tel + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy=" + createdBy +
                ", lastModifiedBy=" + lastModifiedBy +
                '}';
    }
}
