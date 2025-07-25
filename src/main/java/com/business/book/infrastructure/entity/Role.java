package com.business.book.infrastructure.entity;

import com.business.book.infrastructure.entity.constants.Roles;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data @Table("roles")
public class Role {
    @PrimaryKey
    private UUID id;

    private Roles name;

    public Role(Roles name) {
        this.name = name;
    }

    @Override
    public java.lang.String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
