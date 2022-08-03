package shop.gaship.gashipshoppingmall.membercoupon.excpetion;

/**
 * 멤버의 쿠폰을 적용했으나, 주문단계에서 멤버의 적용된 주문이 발견되지 못했을 때 일으키는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class MemberCouponNotFoundException extends RuntimeException {
    private static final String MESSAGE = "회원의 쿠폰이 존재하지 않습니다.";

    public MemberCouponNotFoundException() {
        super(MESSAGE);
    }
}
