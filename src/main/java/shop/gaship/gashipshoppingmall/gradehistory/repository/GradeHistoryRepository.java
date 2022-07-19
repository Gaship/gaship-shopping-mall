package shop.gaship.gashipshoppingmall.gradehistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.gradehistory.entity.GradeHistory;

/**
 * 등급이력 Repository.
 *
 * @author : 김세미
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see shop.gaship.gashipshoppingmall.gradehistory.repository.GradeHistoryRepositoryCustom
 * @since 1.0
 */
public interface GradeHistoryRepository
        extends JpaRepository<GradeHistory, Integer>, GradeHistoryRepositoryCustom {
}
