package shop.gaship.gashipshoppingmall.repairSchedule.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.addressLocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.dayLabor.entity.QDayLabor;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.QRepairSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.repository.custom.RepairScheduleRepositoryCustom;

import java.time.LocalDate;
import java.util.List;

/**
 * packageName     : shop.gaship.gashipshoppingmall.repairSechedule.repository.impl
 * fileName       : RepairScheduleRepositoryImpl
 * author         : 유호철
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE             AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철               최초 생성
 */

public class RepairScheduleRepositoryImpl extends QuerydslRepositorySupport implements
        RepairScheduleRepositoryCustom {

    public RepairScheduleRepositoryImpl() {
        super(RepairSchedule.class);
    }

    @Override
    public List<GetRepairScheduleResponseDto> findAllByDate(LocalDate date) {
        QRepairSchedule repairSchedule = QRepairSchedule.repairSchedule;
        QDayLabor dayLabor = QDayLabor.dayLabor;
        QAddressLocal addressLocal = QAddressLocal.addressLocal;

        return from(repairSchedule)
                .leftJoin(repairSchedule.dayLabor, dayLabor)
                .innerJoin(dayLabor.addressLocal, addressLocal)
                .where(repairSchedule.pk.date.eq(date))
                .select(
                        Projections.bean(GetRepairScheduleResponseDto.class,
                                addressLocal.addressName.as("localName"),
                                repairSchedule.pk.date.as("localDate"),
                                repairSchedule.labor.as("labor")
                        )
                )
                .fetch();
    }

    @Override
    public Page<GetRepairScheduleResponseDto> findAllSortDate(Pageable pageable) {
        QRepairSchedule repairSchedule = QRepairSchedule.repairSchedule;
        QDayLabor dayLabor = QDayLabor.dayLabor;
        QAddressLocal addressLocal = QAddressLocal.addressLocal;

        QueryResults<GetRepairScheduleResponseDto> result = from(
                repairSchedule)
                .leftJoin(repairSchedule.dayLabor, dayLabor)
                .innerJoin(dayLabor.addressLocal, addressLocal)
                .select(
                        Projections.bean(GetRepairScheduleResponseDto.class,
                                addressLocal.addressName.as("localName"),
                                repairSchedule.pk.date.as("localDate"),
                                repairSchedule.labor.as("labor")
                        )
                )
                .orderBy(repairSchedule.pk.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
