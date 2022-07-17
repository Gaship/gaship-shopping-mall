package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : DeliveryType
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
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
