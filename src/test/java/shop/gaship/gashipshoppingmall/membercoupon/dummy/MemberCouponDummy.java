package shop.gaship.gashipshoppingmall.membercoupon.dummy;

import java.time.LocalDateTime;
import shop.gaship.gashipshoppingmall.coupon.dummy.CouponDummy;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.membercoupon.entity.MemberCoupon;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
public class MemberCouponDummy {
    public static MemberCoupon dummy(){
        return MemberCoupon.builder()
            .coupon(CouponDummy.dummy())
            .couponStatusCode(StatusCodeDummy.dummy())
            .expirationDatetime(LocalDateTime.now())
            .member(MemberDummy.dummy())
            .build();
    }
}
