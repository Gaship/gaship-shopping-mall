package shop.gaship.gashipshoppingmall.addressLocal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;

import javax.persistence.*;
import java.util.List;

/**
 * packageName    : shop.gaship.gashipshoppingmall.address.entity fileName       : AddressLocal
 * author         : HoChul date           : 2022/07/08 description    :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2022/07/08        HoChul 최초 생성
 */
@Entity
@Table(name = "address_locals")
@NoArgsConstructor
@Getter
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "upperLocal")
    private List<AddressLocal> subLocal;

    public AddressLocal(String addressName, Integer level, boolean allowDelivery, DayLabor dayLabor, AddressLocal upperLocal, List<AddressLocal> subLocal) {
        this.addressName = addressName;
        this.level = level;
        this.allowDelivery = allowDelivery;
        this.dayLabor = dayLabor;
        this.upperLocal = upperLocal;
        this.subLocal = subLocal;
    }

    /**
     * methodName : registerDayLoabor
     * author : 유호철
     * description : DayLoabor 수정시 / 등록시
     *
     * @param dayLabor DayLabor
     */
    public void registerDayLabor(DayLabor dayLabor) {
        this.dayLabor = dayLabor;
    }

    /**
     * methodName : registerUpperLocal
     * author : 유호철
     * description : 상위주소 수정시
     *
     * @param addressLocal AddressLocal
     */
    public void registerUpperLocal(AddressLocal addressLocal) {
        this.upperLocal = addressLocal;
    }

    /**
     * methodName : addSubLocal
     * author : 유호철
     * description : 하위주소에 값이 추가될경우
     *
     * @param list AddressLocal
     */
    public void addSubLocal(List<AddressLocal> list) {
        this.subLocal = list;
    }

    public void allowDelivery(boolean allowDelivery) {
        this.allowDelivery = allowDelivery;
    }
}
