package shop.gaship.gashipshoppingmall.category.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 *
 * 카테고리 생성 요청에 담기는 데이터 객체
 *
 * @author : 김보민
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CategoryCreateRequestDto {
    @NotBlank
    @Length(max = 20)
    private String name;

    @Min(1)
    private Integer upperCategoryNo;
}
