package shop.gaship.gashipshoppingmall.addresslist.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * The type Address list.
 *
 * @author 최정우
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@Builder
@Table(name = "Address_lists", indexes = @Index(columnList = "addressListNo"))
@ToString
public class AddressList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer addressListNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_local_no", nullable = false)
    private AddressLocal addressLocal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "status_code_no", nullable = false)
    private StatusCode statusCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String zipCode;

    /**
     * AddressList(배송지주소) 엔티티를 생성하는 생성자입니다.
     *
     * @param addressListNo 배송지주소 목록의 고유 번호입니다.
     * @param addressLocal 주소지역 엔티티입니다.
     * @param member 멤버 엔티티입니다.
     * @param statusCode 배송상태를 뜻하는 상태코드 엔티티입니다.
     * @param address 도로명 주소입니다.
     * @param addressDetail 상세 주소입니다.
     * @param zipCode 주소번호입니다.
     */
    @Builder
    public AddressList(Integer addressListNo, AddressLocal addressLocal, Member member,
                       StatusCode statusCode, String address, String addressDetail,
                       String zipCode) {
        this.addressListNo = addressListNo;
        this.addressLocal = addressLocal;
        this.member = member;
        this.statusCode = statusCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }

    /**
     * 해당 배송지목록의 상태값을 변경하는 메서드입니다.
     *
     * @param deleteStatus 배송지목록의 상태를 변경하고자 할때 변경하고자하는 상태값을 담고있는 매개변수 입니다.
     */
    public void modifyStatusToDelete(StatusCode deleteStatus) {
        this.statusCode = deleteStatus;
    }
}
