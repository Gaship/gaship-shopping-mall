package shop.gaship.gashipshoppingmall.dayLabor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;

/**
 * packageName    : shop.gaship.gashipshoppingmall.address.entity fileName       : DayLabor author
 *       : HoChul date           : 2022/07/08 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/08        HoChul
 * 최초 생성
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "day_labor")
public class DayLabor {

    @Id
    @Column(name = "address_local_no")
    private Integer addressNo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "address_local_no")
    private AddressLocal addressLocal;

    @Column(name = "day_max_labor")
    private Integer maxLabor;

    public DayLabor(Integer addressNo,Integer maxLabor){
        this.addressNo = addressNo;
        this.maxLabor = maxLabor;
    }

    /**
     * methodName : fixLocation
     * author : 유호철
     * description : 지역 수정
     *
     * @param addressLocal AddressLocal
     */
    public void fixLocation(AddressLocal addressLocal) {
        this.addressNo = addressLocal.getAddressNo();
        this.addressLocal = addressLocal;
    }
}
