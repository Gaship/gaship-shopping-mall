package shop.gaship.gashipshoppingmall.category.repository.custom;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;

/**
 * 카테고리 레퍼지토리 커스텀
 *
 * @author : 김보민
 * @since 1.0
 */
@NoRepositoryBean
public interface CategoryRepositoryCustom {

    Optional<CategoryResponseDto> findCategoryById(Integer categoryNo);

    List<CategoryResponseDto> findAllCategories();

    List<CategoryResponseDto> findAllLowerCategories(Integer categoryNo);
}
