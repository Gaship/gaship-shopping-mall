package shop.gaship.gashipshoppingmall.category.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 카테고리 생성 요청에 담기는 데이터 객체입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryCreateRequestDto {
    @NotBlank(message = "카테고리 이름은 필수 입력 값입니다.")
    @Length(max = 20, message = "카테고리 이름은 20자 이하여야 합니다.")
    private String name;

    @Size(min = 1, max = 2, message = "카테고리는 총 3종류입니다. (대분류, 중분류, 소분류)")
    private Integer upperCategoryNo;
}
