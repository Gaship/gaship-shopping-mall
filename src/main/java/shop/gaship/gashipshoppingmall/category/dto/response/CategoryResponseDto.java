package shop.gaship.gashipshoppingmall.category.dto.response;

import lombok.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.dto
 * fileName       : CategoryDto
 * author         : 김보민
 * date           : 2022-07-10
 * description    : 카테고리 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-10        김보민       최초 생성
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
    public CategoryResponseDto(Integer no, String name, Integer level, Integer upperCategoryNo, String upperCategoryName) {
        this.no = no;
        this.name = name;
        this.level = level;
        this.upperCategoryNo = upperCategoryNo;
        this.upperCategoryName = upperCategoryName;
    }
}
