package shop.gaship.gashipshoppingmall.order.entity;

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
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.member.entity.Member;

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

    @NotNull
    @Column(name = "order_datetime")
    private LocalDateTime orderDatetime;

    @NotNull
    private String receiptName;

    @NotNull
    private String receiptPhoneNumber;

    private String receiptSubPhoneNumber;

    private String deliveryRequest;

    private String orderPaymentKey;

    private Long totalOrderAmount;

    /**
     * 특정값을 통해 생성자를 만들메서드입니다.
     *
     * @param member             멤버가 기입됩니다.
     * @param addressList        주소리스트가 기입됩니다.
     * @param receiptName        수령자명이 기입됩니다.
     * @param receiptPhoneNumber 수령인 번호가 기입됩니다.
     */
    @Builder
    public Order(Member member, AddressList addressList,
                 String receiptName, String receiptPhoneNumber) {
        this.member = member;
        this.addressList = addressList;
        this.orderDatetime = LocalDateTime.now();
        this.receiptName = receiptName;
        this.receiptPhoneNumber = receiptPhoneNumber;
    }
}
