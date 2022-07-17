package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 상품에 사용되는 배송 형태값
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public enum DeliveryType {
    PARCEL("택배"), CONSTRUCTION("시공");

    public static final String GROUP = "배송형태";

    private final String value;

    DeliveryType(String value) {
        this.value = value;
    }
}