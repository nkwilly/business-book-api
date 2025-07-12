package ink.yowyob.business.book.infrastructure.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table("super_admins")
public class SuperAdmin extends User {
    
}
