package shop.gaship.gashipshoppingmall.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.category.entity.Category;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.repository
 * fileName       : CategoryRepository
 * author         : qnt01
 * date           : 2022-07-09
 * description    : 카테고리 레퍼지토리 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-08        qnt01       최초 생성
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
