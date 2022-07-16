package shop.gaship.gashipshoppingmall.category.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;

/**
 * 카테고리 서비스 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface CategoryService {

    void addRootCategory(CategoryCreateRequestDto createRequest);

    void addLowerCategory(CategoryCreateRequestDto createRequest);

    void modifyCategory(CategoryModifyRequestDto modifyRequest);

    CategoryResponseDto findCategory(Integer categoryNo);

    List<CategoryResponseDto> findCategories();

    void removeCategory(Integer categoryNo);

    List<CategoryResponseDto> findLowerCategories(Integer categoryNo);
}
