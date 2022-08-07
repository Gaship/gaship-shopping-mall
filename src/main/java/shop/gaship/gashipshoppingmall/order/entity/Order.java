package shop.gaship.gashipshoppingmall.order.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.lang.Nullable;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;

/**
 * 주문 entity class.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_lists_no", nullable = false)
    private AddressList addressList;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
        orphanRemoval = true, cascade = CascadeType.REMOVE)
    private final List<OrderProduct> orderProducts = new ArrayList<>();

    @NotNull
    private LocalDateTime orderDatetime;

    @NotNull
    private String receiptName;

    @NotNull
    private String receiptPhoneNumber;

    private String receiptSubPhoneNumber;

    private String deliveryRequest;

    private String orderPaymentKey;

    @ColumnDefault("0")
    private Long totalOrderAmount;

    /**
     * hibernate의 save를 통한 persist가 진행되기 전에 값을 비교하여 null이면 기본값 0을 사용한다.
     */
    @PrePersist
    public void initTotalOrderAmount() {
        this.totalOrderAmount = Objects.isNull(totalOrderAmount) ? 0 : this.totalOrderAmount;
    }

    /**
     * 주문을 생성하는 생성자입니다.
     *
     * @param member 누가 주문했는가에 대한 멤버 엔티티 객체입니다.
     * @param addressList 어디로 배송을 원하는지에 대한 배송지 목록 엔티티 객체입니다.
     * @param receiptName 실제 수령자 이름입니다.
     * @param receiptPhoneNumber 실제 수령자의 전화번호입니다.
     * @param receiptSubPhoneNumber 실제 수령자의 부 전화번호입니다. (선택)
     * @param deliveryRequest 배송요청 사항입니다. (선택)
     */
    @Builder
    public Order(Member member, AddressList addressList,
                 String receiptName, String receiptPhoneNumber,
                 @Nullable String receiptSubPhoneNumber,
                 @Nullable String deliveryRequest
                 ) {
        this.member = member;
        this.addressList = addressList;
        this.orderDatetime = LocalDateTime.now();
        this.receiptName = receiptName;
        this.receiptPhoneNumber = receiptPhoneNumber;
        this.receiptSubPhoneNumber = receiptSubPhoneNumber;
        this.deliveryRequest = deliveryRequest;
    }

    public void updateTotalOrderAmount(Long totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }
}
