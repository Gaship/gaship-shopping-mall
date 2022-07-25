package shop.gaship.gashipshoppingmall.category.dto.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카테고리 조회 응답 시 담기는 데이터 객체입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryResponseDto {
    private Integer no;
    private String name;
    private Integer level;
    private Integer upperCategoryNo;
    private String upperCategoryName;

    @Builder
    public CategoryResponseDto(Integer no,
                               String name,
                               Integer level,
                               Integer upperCategoryNo,
                               String upperCategoryName) {
        this.no = no;
        this.name = name;
        this.level = level;
        this.upperCategoryNo = upperCategoryNo;
        this.upperCategoryName = upperCategoryName;
    }
}
