package shop.gaship.gashipshoppingmall.addresslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import javax.persistence.*;

/**
 * @author 최정우
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Address_lists")
public class AddressList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer addressListNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_local_no", nullable = false)
    private AddressLocal addressLocal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code_no", nullable = false)
    private StatusCode statusCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String zipCode;

    public void modifyStatusToDelete(StatusCode deleteStatus) {
        this.statusCode = deleteStatus;
    }
}
