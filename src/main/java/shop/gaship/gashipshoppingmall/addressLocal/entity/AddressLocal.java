package shop.gaship.gashipshoppingmall.addressLocal.entity;

import java.util.ArrayList;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;

/**
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_address_local_no")
    private AddressLocal upperLocal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "upperLocal")
    private List<AddressLocal> subLocal = new ArrayList<>();

    public AddressLocal(String addressName, Integer level, boolean allowDelivery, DayLabor dayLabor,
                        AddressLocal upperLocal) {
        this.addressName = addressName;
        this.level = level;
        this.allowDelivery = allowDelivery;
        this.dayLabor = dayLabor;
        this.upperLocal = upperLocal;
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
    public void updateSubLocal(List<AddressLocal> list) {
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
