package shop.gaship.gashipshoppingmall.addressLocal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.config.dayLabor.entity.DayLabor;

import javax.persistence.*;
import java.util.List;

/**
 *
 * 데이터베이스에 있는 주소지와 연동하기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
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
     * 지역별물량을 수정/등록하기위한 메서드입니다.
     *
     * @param dayLabor 지역별물량에대한 정보가 들어있습니다.
     * @author 유호철
     */
    public void registerDayLabor(DayLabor dayLabor) {
        this.dayLabor = dayLabor;
    }

    /**
     * 상위주소 수정시 사용되는 메서드입니다.
     *
     * @param addressLocal 주소지정보를담고있습니다.
     * @author 유호철
     */
    public void registerUpperLocal(AddressLocal addressLocal) {
        this.upperLocal = addressLocal;
    }

    /**
     * 하위주소가 추가될경우에 사용됩니다.
     *
     * @param list AddressLocal
     * @author 유호철
     */
    public void addSubLocal(List<AddressLocal> list) {
        this.subLocal = list;
    }

    /**
     * 배송여부의 수정이있을때 사용됩니다.
     *
     * @param allowDelivery 배송가능/불가능한 정보를 가지고있습니다.
     * @author 유호철
     */
    public void allowDelivery(boolean allowDelivery) {
        this.allowDelivery = allowDelivery;
    }
}
