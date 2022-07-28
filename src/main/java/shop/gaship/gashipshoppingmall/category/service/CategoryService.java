package shop.gaship.gashipshoppingmall.category.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;

/**
 * 카테고리 서비스 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface CategoryService {
    /**
     * root 카테고리 추가 메서드입니다.
     *
     * @param createRequest 카테고리 생성 요청
     * @author 김보민
     */
    void addRootCategory(CategoryCreateRequestDto createRequest);

    /**
     * 하위 카테고리 추가 메서드입니다.
     *
     * @param createRequest 카테고리 생성 요청
     * @author 김보민
     */
    void addLowerCategory(CategoryCreateRequestDto createRequest);

    /**
     * 카테고리 수정 메서드입니다.
     *
     * @param modifyRequest 카테고리 수정 요청
     * @author 김보민
     */
    void modifyCategory(CategoryModifyRequestDto modifyRequest);

    /**
     * 카테고리 단건 조회 메서드입니다.
     *
     * @param categoryNo 조회할 카테고리 번호
     * @return CategoryResponseDto 카테고리 데이터
     * @throws CategoryNotFoundException 카테고리를 못찾음
     * @author 김보민
     */
    CategoryResponseDto findCategory(Integer categoryNo);

    /**
     * 카테고리 전체 조회 메서드입니다.
     *
     * @return list 카테고리 목록 데이터
     * @author 김보민
     */
    List<CategoryResponseDto> findCategories();

    /**
     * 하위 카테고리 조회 메서드입니다.
     *
     * @param categoryNo 하위카테고리를 조회할 상위 카테고리 번호
     * @throws CategoryNotFoundException            카테고리를 못찾음
     * @author 김보민
     */
    void removeCategory(Integer categoryNo);

    /**
     * 하위 카테고리 조회 메서드입니다.
     *
     * @param categoryNo 하위 카테고리를 조회할 상위 카테고리
     * @return list 하위 카테고리 목록
     * @author 김보민
     */
    List<CategoryResponseDto> findLowerCategories(Integer categoryNo);
}
