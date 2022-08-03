package shop.gaship.gashipshoppingmall.coupon.dummy;

import shop.gaship.gashipshoppingmall.coupon.entity.Coupon;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
public class CouponDummy {
    public static Coupon dummy(){
        return Coupon.builder()
            .condition("VIP")
            .name("첫 가입 쿠폰")
            .expirationPeriod(12)
            .sendStatusCode(StatusCodeDummy.dummy())
            .warrantyPeriod(12)
            .build();
    }
}
