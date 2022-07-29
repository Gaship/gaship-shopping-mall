package shop.gaship.gashipshoppingmall.membercoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.membercoupon.entity.MemberCoupon;

/**
 * 회원쿠폰 레퍼지토리 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Integer> {
}
