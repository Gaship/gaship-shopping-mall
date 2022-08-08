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
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
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

    private Integer cancellationAmount;

    private String employeeName;

    @OneToOne(mappedBy = "orderProduct", fetch = FetchType.LAZY)
    private ProductReview review;

    /**
     * Instantiates a new Order product.
     *
     * @param product                the product
     * @param order                  the order
     * @param orderStatusCode        the order status code
     * @param warrantyExpirationDate the warranty expiration date
     * @param amount                 the amount
     */
    @Builder
    public OrderProduct(Product product, Order order, StatusCode orderStatusCode,
                        LocalDate warrantyExpirationDate, Long amount) {
        this.product = product;
        this.order = order;
        this.orderStatusCode = orderStatusCode;
        this.warrantyExpirationDate = warrantyExpirationDate;
        this.amount = amount;
    }
}
