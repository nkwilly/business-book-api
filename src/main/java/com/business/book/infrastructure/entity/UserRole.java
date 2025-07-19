package com.business.book.infrastructure.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data @Table("user_roles")
public class UserRole {
    @PrimaryKey
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Column("role_id")
    private UUID roleId;

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}