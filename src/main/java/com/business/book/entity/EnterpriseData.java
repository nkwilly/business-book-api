package com.business.book.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

/**
 * À chaque fois qu'une entreprise est appelée pour être renvoyé à
 * l'utilisateur, viewNumbers doit être incrémenter de 1. Il y a la méthode
 * incrementView
 * du repository qui permet de faire cela il faut juste l'appeler;
 */
@Data
@Table("enterprise_data")
@ToString
public class EnterpriseData {
    @PrimaryKey
    private UUID id;

    private UUID enterpriseId;

    private Long viewsNumbers;

    private boolean hindered;
}