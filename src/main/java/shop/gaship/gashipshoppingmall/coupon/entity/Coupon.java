package shop.gaship.gashipshoppingmall.coupon.entity;

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
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 쿠폰 엔티티입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_no")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_status_no")
    private StatusCode sendStatusCode;

    @NotNull
    private String name;

    @NotNull
    private Integer warrantyPeriod;

    @NotNull
    private String condition;

    private LocalDateTime registerDatetime;

    @NotNull
    private Integer expirationPeriod;

    @Builder
    public Coupon(StatusCode sendStatusCode, String name, Integer warrantyPeriod,
                  String condition, Integer expirationPeriod) {
        this.sendStatusCode = sendStatusCode;
        this.name = name;
        this.warrantyPeriod = warrantyPeriod;
        this.condition = condition;
        this.expirationPeriod = expirationPeriod;
    }
}
