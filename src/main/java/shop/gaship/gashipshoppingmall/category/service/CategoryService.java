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
    /**
     * methodName : addCategory
     * author : 김보민
     * description : 카테고리 생성
     *
     * @param createRequest category create request
     */
    void addCategory(CategoryCreateRequestDto createRequest);

    /**
     * methodName : modifyCategory
     * author : 김보민
     * description : 카테고리 수정
     *
     * @param categoryNo category no
     * @param modifyRequest category modify request
     */
    void modifyCategory(CategoryModifyRequestDto modifyRequest);

    /**
     * methodName : findCategory
     * author : 김보민
     * description : 카테고리 단건 조회
     *
     * @param categoryNo category no
     */
    CategoryResponseDto findCategory(Integer categoryNo);

    /**
     * methodName : findCategories
     * author : 김보민
     * description : 카테고리 다건 조회
     *
     */
    List<CategoryResponseDto> findCategories();

    /**
     * methodName : removeCategory
     * author : 김보민
     * description : 카테고리 삭제
     *
     * @param categoryNo category no
     */
    void removeCategory(Integer categoryNo);
}
