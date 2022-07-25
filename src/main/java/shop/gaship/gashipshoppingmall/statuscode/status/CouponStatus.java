package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 회원쿠폰에 사용되는 쿠폰 상태값
 *
 * @author : 김세미
 * @since 1.0
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
