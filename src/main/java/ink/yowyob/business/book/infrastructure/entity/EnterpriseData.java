package ink.yowyob.business.book.infrastructure.entity;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

/**
 * À chaque fois qu'une entreprise est appelée pour être renvoyé à
 * l'utilisateur, viewNumbers doit être incrémenter de 1. Il y a la méthode
 * incrementView
 * du repository qui permet de faire cela il faut juste l'appeler;
 */
@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
@Table("enterprise_data") @ToString
public class EnterpriseData {
    @PrimaryKey
    @Column("enterprise_id")
    private UUID enterpriseId;

    @Column("view_numbers")
    private Long viewsNumbers;

    private boolean hindered;
}