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

    @Builder
    public MemberCoupon(Member member,
                        StatusCode couponStatusCode,
                        Coupon coupon, LocalDateTime expirationDatetime) {
        this.member = member;
        this.couponStatusCode = couponStatusCode;
        this.coupon = coupon;
        this.expirationDatetime = expirationDatetime;
    }
}
