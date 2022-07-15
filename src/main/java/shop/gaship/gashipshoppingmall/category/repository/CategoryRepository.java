package shop.gaship.gashipshoppingmall.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.repository.custom.CategoryRepositoryCustom;


/**
 *
 * 카테고리 jpa 레퍼지토리
 *
 * @author : 김보민
 * @since 1.0
 */
public interface CategoryRepository
        extends JpaRepository<Category, Integer>, CategoryRepositoryCustom {
}
