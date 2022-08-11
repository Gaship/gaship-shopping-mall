package shop.gaship.gashipshoppingmall.order.dto.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문 등록이 완료되고 결제를 위한 응답 DTO 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class OrderResponseDto {
    @NotNull
    @Min(value = 0, message = "상품금액은 0원 이상입니다.")
    private Long amount; // 실제값은 totalAmount

    @NotNull(message = "주문번호가 잘못되었습니다.")
    @Min(value = 0, message = "주문번호가 잘못되었습니다.")
    private Integer orderId; // 실제값은 orderNo

    @NotBlank(message = "주문 품목이 비어있습니다.")
    private String orderName;

    @NotBlank(message = "주문자 성명을 기입해주세요.")
    private String customerName;
}
