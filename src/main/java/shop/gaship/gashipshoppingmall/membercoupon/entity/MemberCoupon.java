package shop.gaship.gashipshoppingmall.membercoupon.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.coupon.entity.Coupon;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 회원쿠폰 엔티티입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_coupons")
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_no")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_status_no")
    private StatusCode couponStatusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private Coupon coupon;

    private LocalDateTime useDatetime;

    @NotNull
    private LocalDateTime expirationDatetime;

    /**
     * 멤버의 보유 쿠폰을 생성합니다.
     *
     * @param member 쿠폰을 줄 멤버 엔티티 객체입니다.
     * @param couponStatusCode 해당 쿠폰의 상태입니다.
     * @param coupon 어떤 쿠폰인지에 대한 쿠폰 엔티티 객체입니다/
     * @param expirationDatetime 쿠폰의 만료시간입니다.
     */
    @Builder
    public MemberCoupon(Member member,
                        StatusCode couponStatusCode,
                        Coupon coupon, LocalDateTime expirationDatetime) {
        this.member = member;
        this.couponStatusCode = couponStatusCode;
        this.coupon = coupon;
        this.expirationDatetime = expirationDatetime;
    }

    /**
     * 멤버의 보유중인 쿠폰이 시용되거나 만료 등에 대한 상태의 변경을 위한 메서드입니다.
     *
     * @param couponStatusCode 쿠폰의 상태 엔티티 객체입니다.
     */
    public void updateMemberCouponStatus(
        StatusCode couponStatusCode) {
        this.couponStatusCode = couponStatusCode;
    }
}
