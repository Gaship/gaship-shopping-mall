package shop.gaship.gashipshoppingmall.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.coupon.entity.Coupon;

/**
 * 쿠폰 레퍼지토리 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
}
