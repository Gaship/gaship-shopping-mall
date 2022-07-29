package shop.gaship.gashipshoppingmall.order.entity;

import java.time.LocalDateTime;
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
    @JoinColumn(name = "order_no")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_lists_no", nullable = false)
    private AddressList addressList;

    @NotNull
    private LocalDateTime orderDateTime;

    @NotNull
    private String receiptName;

    @NotNull
    private String receiptPhoneNumber;

    private String receiptSubPhoneNumber;

    private String deliveryRequest;

    /**
     * Instantiates a new Order.
     *
     * @param member             the member
     * @param addressList        the address list
     * @param receiptName        the receipt name
     * @param receiptPhoneNumber the receipt phone number
     */
    @Builder
    public Order(Member member, AddressList addressList,
                 String receiptName, String receiptPhoneNumber) {
        this.member = member;
        this.addressList = addressList;
        this.orderDateTime = LocalDateTime.now();
        this.receiptName = receiptName;
        this.receiptPhoneNumber = receiptPhoneNumber;
    }
}
