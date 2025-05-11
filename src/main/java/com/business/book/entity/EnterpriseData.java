package com.business.book.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

/**
 * À chaque fois qu'une entreprise est appelée pour être renvoyé à l'utilisateur, viewNumbers doit être incrémenter de 1. Il y a la méthode incrementView
 * du repository qui permet de faire cela il faut juste l'appeler;
 */
@Data @Builder
@Table("enterprise_data") @ToString
public class EnterpriseData {
    @PrimaryKey
    private UUID enterpriseId;

    private Long viewsNumbers;
}