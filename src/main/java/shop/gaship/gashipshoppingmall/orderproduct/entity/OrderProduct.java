package shop.gaship.gashipshoppingmall.orderproduct.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.membercoupon.entity.MemberCoupon;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 주문상품 entity class.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_no")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_no")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_no", nullable = false)
    private StatusCode orderStatusCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_coupon_no")
    private MemberCoupon memberCoupon;

    @NotNull
    private LocalDate warrantyExpirationDate;

    @NotNull
    private Long amount;

    private LocalDate hopeDate;

    private LocalDateTime cancellationDatetime;

    private String employeeName;

    private Integer trackingNo;

    /**
     * 쿠폰이 적용되지않은 경우에 생성하는 생성자입니다.
     *
     * @param product                제품 엔티티
     * @param order                  주문 엔티티
     * @param orderStatusCode        주문 상태 엔티티
     * @param warrantyExpirationDate 제품 보증 만료기간
     * @param amount                 가격
     */
    @Builder
    public OrderProduct(Product product, Order order, StatusCode orderStatusCode,
                        LocalDate warrantyExpirationDate, Long amount, LocalDate hopeDate) {
        this.product = product;
        this.order = order;
        this.orderStatusCode = orderStatusCode;
        this.warrantyExpirationDate = warrantyExpirationDate;
        this.amount = amount;
        this.hopeDate = hopeDate;
    }

    /**
     * 멤버의 쿠폰을 주문 상품에 적용하는 메서드입니다.
     *
     * @param memberCoupon 멤버가 보유 중이며, 적용할 쿠폰엔티티 객체입니다.
     */
    public void applyMemberCoupon(MemberCoupon memberCoupon) {
        this.memberCoupon = memberCoupon;
        this.warrantyExpirationDate =
            this.warrantyExpirationDate.plusMonths(memberCoupon.getCoupon().getExpirationPeriod());
    }
}
