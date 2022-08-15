package shop.gaship.gashipshoppingmall.orderproduct.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 쿠폰 사용취소 이벤트 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class CouponUseCanceledEvent {
    private List<Integer> couponNos;
}
