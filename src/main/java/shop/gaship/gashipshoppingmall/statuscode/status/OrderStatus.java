package shop.gaship.gashipshoppingmall.statuscode.status;

import java.util.Objects;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.statuscode.exception.InvalidOrderStatusException;

/**
 * .
 * 주문상품에 사용되는 주문 상태 값
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public enum OrderStatus {
    DELIVERY_PREPARING("배송준비중"), SHIPPING("배송중"), DELIVERY_COMPLETE("배송완료"),
    EXCHANGE_RECEPTION("교환접수"), CANCELLATION("취소접수"), RETURN_RECEPTION("반품접수"),
    EXCHANGE_COMPLETE("교환완료"), CANCEL_COMPLETE("취소완료"), RETURN_COMPLETE("반품완료"),
    PURCHASE_CONFIRMATION("구매확정");

    public static final String GROUP = "주문상태";

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    /**
     * 주문 취소가 가능한지 주문상품의 상태를 체크하는 메서드입니다.
     *
     * @param currentOrder 체크할 현재 주문상품의 상태입니다.
     */
    public static void checkCancelableOrder(String currentOrder) {
        if (!Objects.equals(DELIVERY_PREPARING.value, currentOrder)
            && !Objects.equals(SHIPPING.value, currentOrder)
            && !Objects.equals(DELIVERY_COMPLETE.value, currentOrder)) {
            String serviceStatus = "취소상태";

            throw new InvalidOrderStatusException(serviceStatus);
        }
    }

    /**
     * 주문 교환이 가능한지 주문상태를 체크하는 메서드입니다.
     *
     * @param currentOrder 체크할 현재 주문상품의 상태입니다.
     */
    public static void checkChangeableOrder(String currentOrder) {
        if (!Objects.equals(SHIPPING.value, currentOrder)
            && !Objects.equals(DELIVERY_COMPLETE.value, currentOrder)) {
            String serviceStatus = "교환 상태";

            throw new InvalidOrderStatusException(serviceStatus);
        }
    }

    /**
     * 주문 취소 실패시 복구가 가능한지 주문상태를 체크하는 메서드입니다.
     *
     * @param currentOrder 체크할 현재 주문상품의 상태입니다.
     */
    public static void checkRecoverableOrder(String currentOrder) {
        if (!Objects.equals(CANCEL_COMPLETE.value, currentOrder)) {
            String serviceStatus = "취소 복구 상태";

            throw new InvalidOrderStatusException(serviceStatus);
        }
    }
}
