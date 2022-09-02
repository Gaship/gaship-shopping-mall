package shop.gaship.gashipshoppingmall.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문 취소시 해당 주문의 결제 식별 키값을 조회하기 위한 요청에 대한
 * 응답 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class CancelOrderResponseDto {
    private String paymentKey;
}
