package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
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
}
