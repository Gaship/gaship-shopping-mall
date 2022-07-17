package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : PaymentStatus
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
 */
@Getter
public enum PaymentStatus {
    FAIL("실패"), SUCCESS("성공");

    public static final String GROUP = "결제상태";

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
