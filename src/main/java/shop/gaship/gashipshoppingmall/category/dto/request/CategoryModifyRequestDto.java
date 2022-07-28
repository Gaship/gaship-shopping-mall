package shop.gaship.gashipshoppingmall.category.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 카테고리 수정 요청에 담기는 데이터 객체.
 *
 * @author : 김보민
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryModifyRequestDto {
    @NotNull
    @Min(1)
    private Integer no;

    @NotBlank(message = "카테고리 이름은 필수 입력 값입니다.")
    @Length(max = 20)
    private String name;
}
