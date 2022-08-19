package shop.gaship.gashipshoppingmall.category.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.gaship.gashipshoppingmall.category.entity.Category;

/**
 * 카테고리 조회 응답 시 담기는 데이터 객체입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDto {
    private Integer no;
    private String name;
    private Integer level;
    private Integer upperCategoryNo;
    private String upperCategoryName;
    private List<CategoryResponseDto> lowerCategories;

    public static CategoryResponseDto dtoToEntity(Category category) {
        CategoryResponseDto response = new CategoryResponseDto();

        response.setNo(category.getNo());
        response.setName(category.getName());
        response.setLevel(category.getLevel());
        response.setLowerCategories(category.getLowerCategories().stream()
                .map(CategoryResponseDto::dtoToEntity)
                .collect(Collectors.toList()));

        return response;
    }
}
