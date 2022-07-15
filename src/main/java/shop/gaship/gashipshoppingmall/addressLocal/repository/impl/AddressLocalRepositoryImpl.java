package shop.gaship.gashipshoppingmall.addressLocal.repository.impl;

import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.custom.AddressLocalRepositoryCustom;

import java.util.List;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.repository.impl fileName       :
 * AddressLocalRepositoryImpl author         : 유호철 date           : 2022/07/14 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/14       유호철       최초
 * 생성
 */
public class AddressLocalRepositoryImpl extends QuerydslRepositorySupport
        implements AddressLocalRepositoryCustom {

    public AddressLocalRepositoryImpl() {
        super(AddressLocal.class);
    }

    @Override
    public List<GetAddressLocalResponseDto> findAllAddress(String addressName) {
        QAddressLocal addressLocal = QAddressLocal.addressLocal;

        return from(addressLocal)
                .where(addressLocal.addressName.contains(addressName))
                .where(addressLocal.level.eq(1))
                .select(Projections.bean(GetAddressLocalResponseDto.class,
                        addressLocal.addressName.as("upperAddressName"),
                        addressLocal.subLocal.any().addressName.as("addressName")))
                .fetch();

    }
}
