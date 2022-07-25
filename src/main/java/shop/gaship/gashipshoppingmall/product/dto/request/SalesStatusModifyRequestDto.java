package shop.gaship.gashipshoppingmall.product.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class SalesStatusModifyRequestDto {
    @Min(1)
    @NotNull
    private Integer productNo;

    @Length(max = 20, message = "상태코드명은 20자 이하여야 합니다.")
    @NotBlank
    private String statusCodeName;
}
