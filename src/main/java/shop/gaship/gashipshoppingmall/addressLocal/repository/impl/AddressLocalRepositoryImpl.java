package shop.gaship.gashipshoppingmall.addressLocal.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.custom.AddressLocalRepositoryCustom;
import shop.gaship.gashipshoppingmall.response.PageResponse;


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

    public AddressLocalRepositoryImpl() {
        super(AddressLocal.class);
    }

    /**
     * 조회된 주소로 전체주소가 조회되는 메서드입니다.
     *
     * @param addressName 검색하고싶은 주소지가 입력됩니다.
     * @return list : 조회된 상위주소, 하위주소들이 반환됩니다.
     * @author 유호철
     */
    @Override
    public PageResponse<GetAddressLocalResponseDto> findAllAddress(
        String addressName, Pageable pageable) {
        QAddressLocal addressLocal = QAddressLocal.addressLocal;

        JPQLQuery<GetAddressLocalResponseDto> query = from(addressLocal)
            .where(addressLocal.addressName.contains(addressName)
                .and(addressLocal.level.eq(1)))
            .select(Projections.bean(GetAddressLocalResponseDto.class,
                addressLocal.addressName.as("upperAddressName"),
                addressLocal.subLocal.any().addressName.as("addressName")));

        List<GetAddressLocalResponseDto> content = query.offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageResponse<>(PageableExecutionUtils.getPage(content, pageable,
            () -> query.fetch()
                .size()));
    }
}
