package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : OrderStatus
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
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
