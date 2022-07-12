package shop.gaship.gashipshoppingmall.category.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;
import shop.gaship.gashipshoppingmall.category.dto.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.CategoryModifyRequestDto;

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
    void createCategory(CategoryCreateRequestDto request);

    void modifyCategory(Integer categoryNo, CategoryModifyRequestDto request);

    CategoryDto getCategory(Integer categoryNo);

    List<CategoryDto> getCategories();

    void removeCategory(Integer categoryNo);
}
