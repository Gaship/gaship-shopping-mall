package shop.gaship.gashipshoppingmall.category.dummy;

import org.springframework.test.util.ReflectionTestUtils;
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
        CategoryResponseDto dummy = new CategoryResponseDto();
        ReflectionTestUtils.setField(dummy, "no", 1);
        ReflectionTestUtils.setField(dummy, "name", "상위 카테고리");
        ReflectionTestUtils.setField(dummy, "level", 1);
        ReflectionTestUtils.setField(dummy, "upperCategoryNo", null);
        ReflectionTestUtils.setField(dummy, "upperCategoryName", null);
        return dummy;
    }

    public static CategoryResponseDto dtoDummy() {
        CategoryResponseDto dummy = new CategoryResponseDto();
        ReflectionTestUtils.setField(dummy, "no", 2);
        ReflectionTestUtils.setField(dummy, "name", "카테고리");
        ReflectionTestUtils.setField(dummy, "level", 2);
        ReflectionTestUtils.setField(dummy, "upperCategoryNo", 1);
        ReflectionTestUtils.setField(dummy, "upperCategoryName", "상위 카테고리");
        return dummy;
    }
}
