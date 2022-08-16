package shop.gaship.gashipshoppingmall.delivery.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.delivery.service.DeliveryService;

/**
 * 배송요청을 발생시키는 이벤트입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class DeliveryEventHandler {
    private final DeliveryService deliveryService;

    @TransactionalEventListener
    public void publishDeliveryRequest(DeliveryEvent event) {
        event.getOrderProductNos().forEach(deliveryService::createTrackingNo);
    }
}
