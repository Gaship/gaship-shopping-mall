package shop.gaship.gashipshoppingmall.orderproduct.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 주문상품 entity class.
 *
 * @author : 김세미, 김민수
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

    @OneToOne(mappedBy = "orderProduct", fetch = FetchType.LAZY)
    private ProductReview review;

    @Column(name = "member_coupon_no")
    private Integer memberCouponNo;

    @NotNull
    private LocalDate warrantyExpirationDate;

    @NotNull
    private Long amount;

    private LocalDate hopeDate;

    private LocalDateTime cancellationDatetime;

    @ColumnDefault("0")
    @NotNull
    private Long cancellationAmount;

    private String employeeName;

    private String trackingNo;

    private Integer paymentCancelHistoryNo;

    private String cancellationReason;

    /**
     * hibernate의 save를 통한 persist가 진행되기 전에 값을 비교하여 null이면 기본값 0을 사용한다.
     */
    @PrePersist
    public void initCancellationAmount() {
        this.cancellationAmount = Objects.isNull(cancellationAmount) ? 0 : this.cancellationAmount;
    }

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
                        LocalDate warrantyExpirationDate, Long amount, LocalDate hopeDate,
                        Integer memberCouponNo) {
        this.product = product;
        this.order = order;
        this.orderStatusCode = orderStatusCode;
        this.warrantyExpirationDate = warrantyExpirationDate;
        this.amount = amount;
        this.hopeDate = hopeDate;
        this.memberCouponNo = memberCouponNo;
    }

    public void updateOrderProductStatus(StatusCode orderProductStatusCode) {
        this.orderStatusCode = orderProductStatusCode;
        this.cancellationDatetime = LocalDateTime.now();
    }

    /**
     * 주문 상품에대한 주문 혹은 반품, 환불등과 같은 주문 취소 행위가 발생시 주문 상태를 바꾸어주기 위한 메서드입니다.
     *
     * @param orderProductStatusCode 바꾸어야하는 주문 상태
     * @param cancellationAmount 취소금액
     */
    public void updateCancellation(StatusCode orderProductStatusCode, Long cancellationAmount,
                                   String cancellationReason, Integer paymentCancelHistoryNo,
                                   LocalDateTime cancellationDatetime) {
        this.orderStatusCode = orderProductStatusCode;
        this.cancellationAmount = cancellationAmount;
        this.cancellationReason = cancellationReason;
        this.paymentCancelHistoryNo = paymentCancelHistoryNo;
        this.cancellationDatetime = cancellationDatetime;
    }

    public void addTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public void acceptedInstallEmployee(Employee employee) {
        this.employee = employee;
        this.employeeName = employee.getName();
    }
}
