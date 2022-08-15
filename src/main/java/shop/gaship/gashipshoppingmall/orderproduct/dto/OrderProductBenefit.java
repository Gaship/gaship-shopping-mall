package shop.gaship.gashipshoppingmall.orderproduct.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주문 상세 품목에 적용 될 혜택을 담을 데이터 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public class OrderProductBenefit {
    private final Integer memberCouponNo;
}
