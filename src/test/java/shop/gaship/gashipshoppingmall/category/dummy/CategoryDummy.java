package shop.gaship.gashipshoppingmall.category.dummy;

import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.entity.Category;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.dummy
 * fileName       : CategoryDummy
 * author         : 김보민
 * date           : 2022-07-11
 * description    : 카테고리 더미
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-11        김보민       최초 생성
 */
public class CategoryDummy {

    /**
     * methodName : upperDummy
     * author : 김보민
     * description : 상위 카테고리 더미
     *
     * @return category
     */
    public static Category upperDummy() {
        return new Category(
                "상위 카테고리",
                1
        );
    }

    /**
     * methodName : dummy
     * author : 김보민
     * description : 카테고리 더미
     *
     * @return category
     */
    public static Category dummy() {
        Category dummy = new Category(
                "카테고리",
                2
        );

        dummy.updateUpperCategory(upperDummy());
        return dummy;
    }

    /**
     * methodName : upperDtoDummy
     * author : 김보민
     * description : 상위 카테고리 dto 더미
     *
     * @return category response dto
     */
    public static CategoryResponseDto upperDtoDummy() {
        return CategoryResponseDto.builder()
                .no(1)
                .name("카테고리")
                .level(1)
                .build();
    }
    
    /**
     * methodName : dtoDummy
     * author : 김보민
     * description : 카테고리 dto 더미
     *
     * @return category dto
     */
    public static CategoryResponseDto dtoDummy() {
        return CategoryResponseDto.builder()
                .no(2)
                .name("카테고리")
                .level(2)
                .upperCategoryNo(1)
                .upperCategoryName("상위 카테고리")
                .build();
    }
}
