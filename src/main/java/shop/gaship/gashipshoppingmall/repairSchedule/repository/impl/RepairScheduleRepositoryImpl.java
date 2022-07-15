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
 * 수리스케줄에대해서 Query Dsl 을 쓰기위한 구현 클래스입니다.
 *
 *
 * @see QuerydslRepositorySupport
 * @see RepairScheduleRepositoryCustom
 * @author : 유호철
 * @since 1.0
 */
public class RepairScheduleRepositoryImpl extends QuerydslRepositorySupport implements
        RepairScheduleRepositoryCustom {

    public RepairScheduleRepositoryImpl() {
        super(RepairSchedule.class);
    }

    /**
     * 날짜를 통해 스케줄정보를 얻는 메소드입니다.
     *
     *
     * @param date 입력될 날짜정보입니다.
     * @return list 날짜정보를 통해 수리스케줄들이 반한됩니다.
     * @author 유호철
     */
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

    /**
     * 일자별로 정렬된 모든 스케줄들이 페이징형태로 표시되기위한 메서드입니다.
     *
     * @param pageable 페이지 정보가 담겨있다.
     * @return page 페이징된 스케줄정보들이 반환됩니다.
     * @author 유호철
     */
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
