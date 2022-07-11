package shop.gaship.gashipshoppingmall.category.service;

import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;
import shop.gaship.gashipshoppingmall.category.request.CategoryCreateRequest;
import shop.gaship.gashipshoppingmall.category.request.CategoryModifyRequest;

import java.util.List;

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
    void createCategory(CategoryCreateRequest request);

    void modifyCategory(Integer categoryNo, CategoryModifyRequest request);

    CategoryDto getCategory(Integer categoryNo);

    List<CategoryDto> getCategories();
}
