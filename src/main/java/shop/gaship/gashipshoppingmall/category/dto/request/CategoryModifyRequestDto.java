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
    @NotNull(message = "categoryNo 는 필수 입력 값입니다.")
    @Min(value = 1, message = "categoryNo 는 0 이하일 수 없습니다.")
    private Integer no;

    @NotBlank(message = "카테고리 이름은 필수 입력 값입니다.")
    @Length(min = 1, max = 20, message = "name 의 길이는 최소 1 최대 10 입니다.")
    private String name;
}
