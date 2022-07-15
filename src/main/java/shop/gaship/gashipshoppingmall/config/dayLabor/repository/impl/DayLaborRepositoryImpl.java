package shop.gaship.gashipshoppingmall.config.dayLabor.repository.impl;

import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.addressLocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.config.dayLabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.config.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.config.dayLabor.repository.custom.DayLaborRepositoryCustom;
import shop.gaship.gashipshoppingmall.dayLabor.entity.QDayLabor;

import java.util.List;

/**
 * 지역별물량을 QueryDsl 을통해 사용하기위한 클래스 구현체입니다.
 *
 * @see QuerydslRepositorySupport
 * @see DayLaborRepositoryCustom
 * @author : 유호철
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
    public List<GetDayLaborResponseDto> findAllDayLabor() {

        QDayLabor dayLabor = QDayLabor.dayLabor;
        QAddressLocal addressLocal = QAddressLocal.addressLocal;

        return from(dayLabor)
                .leftJoin(dayLabor, addressLocal.dayLabor)
                .select(
                        Projections.bean(GetDayLaborResponseDto.class,
                                addressLocal.addressName.as("local"),
                                dayLabor.maxLabor))
                .fetch();
    }
}
