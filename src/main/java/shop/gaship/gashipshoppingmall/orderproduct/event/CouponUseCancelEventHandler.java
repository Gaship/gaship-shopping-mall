package shop.gaship.gashipshoppingmall.orderproduct.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.orderproduct.adapter.OrderProductAdapter;

/**
 * 쿠폰사용 요청을 실행하는 이벤트 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CouponUseCancelEventHandler {
    private final OrderProductAdapter orderProductAdapter;


    /**
     * 쿠폰 사용을 요청하는 이벤트 메서드입니다.
     *
     * @param event 쿠폰 사용이벤트입니다.
     */
    @TransactionalEventListener
    public void handle(CouponUseCancelEvent event) {
        orderProductAdapter.useCancelCouponRequest(event.getCouponNos());
    }
}
