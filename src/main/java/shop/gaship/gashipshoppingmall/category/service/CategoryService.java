package shop.gaship.gashipshoppingmall.category.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.service
 * fileName       : CategoryService
 * author         : 김보민
 * date           : 2022-07-09
 * description    : 카테고리 서비스 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-09        김보민       최초 생성
 */
public interface CategoryService {
    void addCategory(CategoryCreateRequestDto createRequest);

    void modifyCategory(CategoryModifyRequestDto modifyRequest);

    CategoryResponseDto findCategory(Integer categoryNo);

    List<CategoryResponseDto> findCategories();

    void removeCategory(Integer categoryNo);
}
