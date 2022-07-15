package shop.gaship.gashipshoppingmall.category.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 *
 * 카테고리 수정 요청에 담기는 데이터 객체
 *
 * @author : 김보민
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CategoryModifyRequestDto {
    @NotNull
    @Min(1)
    private Integer no;

    @NotBlank
    @Length(max = 20)
    private String name;
}
