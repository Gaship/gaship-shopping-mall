package shop.gaship.gashipshoppingmall.dayLabor.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.addressLocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.entity.QDayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.custom.DayLaborRepositoryCustom;

/**
 *packageName    : shop.gaship.gashipshoppingmall.dayLabor.repository.impl
 * fileName       : DayLaborRepositoryImpl
 * author         : 유호철
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초 생성
 */
//
public class DayLaborRepositoryImpl extends QuerydslRepositorySupport implements
    DayLaborRepositoryCustom {

    public DayLaborRepositoryImpl() {
        super(DayLabor.class);
    }

    @Override
    public List<GetDayLaborResponseDto> findAllDayLabor() {

        QDayLabor dayLabor = QDayLabor.dayLabor;
        QAddressLocal addressLocal = QAddressLocal.addressLocal;

        return from(dayLabor)
            .leftJoin(dayLabor,addressLocal.dayLabor)
            .select(
                Projections.bean(GetDayLaborResponseDto.class,
                addressLocal.addressName.as("local"),
                dayLabor.maxLabor))
            .fetch();
    }
}
