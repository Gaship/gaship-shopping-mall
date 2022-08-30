package shop.gaship.gashipshoppingmall.orderproduct.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;

/**
 * 주문 상품등록을 진행시키는 이벤트 핸들러입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class OrderProductRegisteredEventHandler {
    private final OrderProductService orderProductService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void saveOrderProduct(OrderProductRegisteredEvent event) {
        orderProductService.registerOrderProduct(event.getOrder(), event.getOrderProducts());
    }
}
