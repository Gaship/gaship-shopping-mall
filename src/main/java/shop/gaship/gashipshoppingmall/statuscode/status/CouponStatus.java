package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : CouponStatus
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
 */
@Getter
public enum CouponStatus {
    USED("사용완료"), AVAILABLE("사용가능"), EXPIRY("기간만료");

    public static final String GROUP = "사용상태";

    private final String value;

    CouponStatus(String value) {
        this.value = value;
    }
}
