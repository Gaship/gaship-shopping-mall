package shop.gaship.gashipshoppingmall.order.dto.request;

import lombok.Getter;

/**
 * 주문 결제까지 성공시 해당 주문의 결제이력을 주입하는 DTO 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
public class OrderSuccessRequestDto {
    private Integer orderNo;
    private String paymentKey;
}
