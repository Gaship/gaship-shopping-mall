package shop.gaship.gashipshoppingmall.orderproduct.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 쿠폰 사용 이벤트를 받는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class CouponUsedEvent {
    List<Integer> couponNos;
}
