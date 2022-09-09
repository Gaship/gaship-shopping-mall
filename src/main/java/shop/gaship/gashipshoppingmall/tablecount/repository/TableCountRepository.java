package shop.gaship.gashipshoppingmall.tablecount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.tablecount.entity.TableCount;

/**
 * @author : 최겸준
 * @since 1.0
 */
public interface TableCountRepository extends JpaRepository<TableCount, String> {
}
