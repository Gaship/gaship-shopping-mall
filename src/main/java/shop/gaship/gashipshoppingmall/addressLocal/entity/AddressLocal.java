package shop.gaship.gashipshoppingmall.addressLocal.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;

/**
 * packageName    : shop.gaship.gashipshoppingmall.address.entity fileName       : AddressLocal
 * author         : HoChul date           : 2022/07/08 description    :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2022/07/08        HoChul 최초 생성
 */
@Entity
@Table(name = "address_locals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressLocal {

    @Id
    @Column(name = "address_local_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressNo;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "level")
    private Integer level;

    @Column(name = "delivery_availability")
    private boolean allowDelivery;

    @OneToOne(mappedBy = "addressLocal")
    private DayLabor dayLabor;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "upper_address_local_no")
    private AddressLocal upperLocal;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "upperLocal")
    private List<AddressLocal> subLocal;

    public AddressLocal(Integer addressNo, String addressName, Integer level,
        boolean allowDelivery) {
        this.addressNo = addressNo;
        this.addressName = addressName;
        this.level = level;
        this.allowDelivery = allowDelivery;
    }
}
