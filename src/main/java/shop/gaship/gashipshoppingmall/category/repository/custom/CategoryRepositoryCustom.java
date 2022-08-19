package shop.gaship.gashipshoppingmall.category.repository.custom;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;

/**
 * 카테고리 레퍼지토리 커스텀입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@NoRepositoryBean
public interface CategoryRepositoryCustom {

    /**
     * 카테고리 단건 조회 메서드입니다.
     *
     * @param categoryNo 조회할 카테괼 번호
     * @return optional 카테고리 정보를 담은 optional
     * @author 김보민
     */
    Optional<CategoryResponseDto> findCategoryById(Integer categoryNo);

    /**
     * 하위 카테고리 조회 메서드입니다.
     *
     * @param categoryNo 하위 카테고리를 조회할 상위 카테고리 번호
     * @return list 하위 카테고리 목록 정보를 담은 list
     * @author 김보민
     */
    List<CategoryResponseDto> findAllLowerCategories(Integer categoryNo);
}
