package shop.gaship.gashipshoppingmall.addresslocal.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressSubLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressUpperLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.custom.AddressLocalRepositoryCustom;


/**
 * 주소지를 QueryDsl 을 통해 사용하는 클래스입니다.
 *
 * @author : 유호철
 * @see QuerydslRepositorySupport
 * @see AddressLocalRepositoryCustom
 * @since 1.0
 */
public class AddressLocalRepositoryImpl extends QuerydslRepositorySupport
    implements AddressLocalRepositoryCustom {
    public static final Integer HIGH_ADDRESS_NO = 1;
    
    public AddressLocalRepositoryImpl() {
        super(AddressLocal.class);
    }

    @Override
    public List<AddressUpperLocalResponseDto> findAllAddress() {
        QAddressLocal addressLocal = QAddressLocal.addressLocal;

        return from(addressLocal)
            .where(addressLocal.level.eq(HIGH_ADDRESS_NO))
            .select(Projections.constructor(AddressUpperLocalResponseDto.class,
                addressLocal.addressNo,
                addressLocal.addressName,
                addressLocal.allowDelivery))
            .fetch();
    }

    @Override
    public List<AddressSubLocalResponseDto> findSubAddress(String addressName) {
        QAddressLocal addressLocal = QAddressLocal.addressLocal;
        QAddressLocal upperAddressLocal = new QAddressLocal("upper");
        return from(addressLocal)
            .innerJoin(addressLocal.upperLocal, upperAddressLocal)
            .where(addressLocal.upperLocal.addressName.eq(addressName))
            .select(Projections.constructor(AddressSubLocalResponseDto.class,
                addressLocal.addressNo,
                addressLocal.addressName))
            .fetch();
    }
}
