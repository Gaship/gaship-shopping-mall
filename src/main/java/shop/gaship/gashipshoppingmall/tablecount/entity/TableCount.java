package shop.gaship.gashipshoppingmall.tablecount.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 최겸준
 * @since 1.0
 */

@Entity
@Table(name="table_counts")
@Getter
public class TableCount {
    @Id
    private String name;

    @Setter
    private Long count;
}
