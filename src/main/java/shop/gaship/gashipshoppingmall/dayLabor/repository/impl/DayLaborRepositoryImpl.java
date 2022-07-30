package shop.gaship.gashipshoppingmall.dayLabor.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.addressLocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.entity.QDayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.custom.DayLaborRepositoryCustom;
import shop.gaship.gashipshoppingmall.response.PageResponse;


/**
 * 지역별물량을 QueryDsl 을통해 사용하기위한 클래스 구현체입니다.
 *
 * @author : 유호철
 * @see QuerydslRepositorySupport
 * @see DayLaborRepositoryCustom
 * @since 1.0
 */
public class DayLaborRepositoryImpl extends QuerydslRepositorySupport implements
    DayLaborRepositoryCustom {

    public DayLaborRepositoryImpl() {
        super(DayLabor.class);
    }

    /**
     * 전체 지역별물량을 조회하기위한 메서드입니다.
     *
     * @return list 지역명과 최대물량이 담겨져있습니다.
     * @author 유호철
     */
    @Override
    public PageResponse<GetDayLaborResponseDto> findAllDayLabor(Pageable pageable) {

        QDayLabor dayLabor = QDayLabor.dayLabor;
        QAddressLocal addressLocal = QAddressLocal.addressLocal;

        JPQLQuery<GetDayLaborResponseDto> query = from(dayLabor)
            .leftJoin(dayLabor, addressLocal.dayLabor)
            .select(
                Projections.bean(GetDayLaborResponseDto.class,
                    addressLocal.addressName.as("local"),
                    dayLabor.maxLabor));

        List<GetDayLaborResponseDto> content =
            query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageResponse<>(PageableExecutionUtils.getPage(content, pageable,
            () -> query.fetch()
                .size()));

    }
}
