package shop.gaship.gashipshoppingmall.config.dayLabor.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.config.dayLabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.config.dayLabor.dto.request.FixDayLaborRequestDto;

import javax.persistence.*;

/**
 * 실제 데이터베이스에있는 지역별물량을 사용하기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
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

    public DayLabor(Integer addressNo, Integer maxLabor) {
        this.addressNo = addressNo;
        this.maxLabor = maxLabor;
    }

    /**
     * 주소지를 수정하기위한 메서드입니다.
     *
     * @param addressLocal 주소지에대한 정보가 들어있습니다.
     * @author 유호철
     */
    public void fixLocation(AddressLocal addressLocal) {
        this.addressNo = addressLocal.getAddressNo();
        this.addressLocal = addressLocal;
    }

    /**
     * 지역별 물량을 등록하기위한 메서드입니다.
     *
     * @param dto 생성시 들어가야할 물량에대한 정보들이 들어있습니다.
     * @author 유호철
     */
    public void registerDayLabor(CreateDayLaborRequestDto dto) {
        this.addressNo = dto.getLocalNo();
        this.maxLabor = dto.getMaxLabor();
    }

    /**
     * 최대일량을 수정하기위한 메서드입니다.
     *
     * @param dto 최대일량에대한 정보가 들어있습니다.
     * @author 유호철
     */
    public void fixMaxLabor(FixDayLaborRequestDto dto) {
        this.maxLabor = dto.getMaxLabor();
    }
}
