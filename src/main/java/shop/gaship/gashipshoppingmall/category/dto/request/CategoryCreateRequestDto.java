package shop.gaship.gashipshoppingmall.category.dto.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CategoryCreateRequestDto {
    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer level;

    private Integer upperCategoryNo;
}
