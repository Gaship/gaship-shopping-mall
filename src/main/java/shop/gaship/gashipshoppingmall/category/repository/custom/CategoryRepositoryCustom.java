package shop.gaship.gashipshoppingmall.category.repository.custom;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.repository.custom
 * fileName       : CategoryRepositoryCustom
 * author         : 김보민
 * date           : 2022-07-11
 * description    : 카테고리 레퍼지토리 커스텀
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-11        김보민       최초 생성
 */
@NoRepositoryBean
public interface CategoryRepositoryCustom {
    Optional<CategoryDto> findCategoryById(Integer categoryNo);

    List<CategoryDto> findAllCategories();
}
