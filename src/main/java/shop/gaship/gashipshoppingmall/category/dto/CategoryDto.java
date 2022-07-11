package shop.gaship.gashipshoppingmall.category.dto;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {
    private Integer no;
    private String name;
    private Integer level;
    private Integer upperCategoryNo;
    private String upperCategoryName;
}
