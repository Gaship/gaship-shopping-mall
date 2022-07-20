package shop.gaship.gashipshoppingmall.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Integer productNo;
    private String statusCodeName;
}
