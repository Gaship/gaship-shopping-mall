package shop.gaship.gashipshoppingmall.product.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 상품 판매상태 수정 요청 dto 클래스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalesStatusModifyRequestDto {
    @Min(value = 1, message = "productNo 는 0 이하일 수 없습니다.")
    @NotNull(message = "productNo 는 필수 입력값입니다.")
    private Integer productNo;

    @Length(max = 20, message = "상태코드명은 20자 이하여야 합니다.")
    @NotBlank(message = "statusCodeName 는 필수 입력값입니다.")
    private String statusCodeName;
}
