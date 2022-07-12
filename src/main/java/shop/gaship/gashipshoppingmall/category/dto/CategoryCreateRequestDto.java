package shop.gaship.gashipshoppingmall.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.dto
 * fileName       : CategoryCreateRequest
 * author         : 김보민
 * date           : 2022-07-09
 * description    : 카테고리 생성 리퀘스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-09        김보민       최초 생성
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryCreateRequestDto {
    private String name;
    private Integer level;
    private Integer upperCategoryNo;
}
