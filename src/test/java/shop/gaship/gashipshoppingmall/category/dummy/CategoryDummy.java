package shop.gaship.gashipshoppingmall.category.dummy;

import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
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
        return Category.builder()
                .name("상위 카테고리")
                .level(1)
                .build();
    }

    /**
     * methodName : dummy
     * author : 김보민
     * description : 카테고리 더미
     *
     * @return category
     */
    public static Category dummy() {
        Category dummy = Category.builder()
                .name("카테고리")
                .level(2)
                .build();

        dummy.updateUpperCategory(upperDummy());
        return dummy;
    }

    /**
     * methodName : dtoDummy
     * author : 김보민
     * description : 카테고리 dto 더미
     *
     * @param categoryNo category no
     * @return category dto
     */
    public static CategoryResponseDto dtoDummy(Integer categoryNo) {
        return CategoryResponseDto.builder()
                .no(categoryNo)
                .name("카테고리")
                .level(1)
                .build();
    }
}
