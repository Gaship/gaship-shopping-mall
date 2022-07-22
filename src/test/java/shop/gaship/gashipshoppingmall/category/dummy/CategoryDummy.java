package shop.gaship.gashipshoppingmall.category.dummy;

import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.entity.Category;

public class CategoryDummy {

    public static Category upperDummy() {
        return new Category(
                "상위 카테고리",
                1
        );
    }

    public static Category dummy() {
        Category dummy = new Category(
                "카테고리",
                2
        );

        dummy.updateUpperCategory(upperDummy());
        return dummy;
    }

    public static Category bottomDummy(){
        Category dummy =  new Category(
                "제일하위",3);
        dummy.updateUpperCategory(dummy());
        return dummy;
    }

    public static CategoryResponseDto upperDtoDummy() {
        return CategoryResponseDto.builder()
                .no(1)
                .name("카테고리")
                .level(1)
                .build();
    }

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
