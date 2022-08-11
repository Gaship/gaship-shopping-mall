package shop.gaship.gashipshoppingmall.orderproduct.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;

/**
 * 주문 제품의 취소를 실행하는 이벤트입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class OrderProductCancelEventHandler {
    private final StatusCodeRepository statusCodeRepository;
    private final OrderProductService orderProductService;

    /**
     * 주문 제품을 취소 상태로 변경합니다.
     *
     * @param event 주문 제품 취소 이벤트 트리거입니다.
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void updateOrderProductStatusToCancellation(OrderProductCancelEvent event) {
        StatusCode cancellationStatusCode =
            statusCodeRepository.findByStatusCodeName(OrderStatus.CANCEL_COMPLETE.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        event.orderProducts.forEach(orderProduct ->
            orderProductService.updateOrderStatus(orderProduct, cancellationStatusCode));
    }
}
