package shop.gaship.gashipshoppingmall.delivery.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.delivery.service.DeliveryService;

/**
 * 배송요청을 발생시키는 이벤트입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryEventHandler {
    private final DeliveryService deliveryService;

    @TransactionalEventListener
    public void publishDeliveryRequest(DeliveryEvent event) {
        log.error("이벤트 리스너가 동작하는지 테스트 : {}", event.getOrderProductNos());
        event.getOrderProductNos().forEach(deliveryService::createTrackingNo);
        log.error("이벤트 리스너가 동작하는지 테스트2 : {}", event.getOrderProductNos());
    }
}
