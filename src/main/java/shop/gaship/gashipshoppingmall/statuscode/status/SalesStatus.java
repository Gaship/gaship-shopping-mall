package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : SalesStatus
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
 */
@Getter
public enum SalesStatus {
    SALE("판매중"), SOLD_OUT("품절"), HIDING("숨김");

    public static final String GROUP = "판매상태";

    private final String value;

    SalesStatus(String value) {
        this.value = value;
    }
}
