package shop.gaship.gashipshoppingmall.repairschedule.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.addresslocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.daylabor.entity.QDayLabor;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.RepairScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairschedule.entity.QRepairSchedule;
import shop.gaship.gashipshoppingmall.repairschedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairschedule.repository.custom.RepairScheduleRepositoryCustom;


/**
 * 수리스케줄에대해서 Query Dsl 을 쓰기위한 구현 클래스입니다.
 *
 * @author : 유호철
 * @see QuerydslRepositorySupport
 * @see RepairScheduleRepositoryCustom
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
     * @param pageable 페이지 정보가 기입됩니다.
     * @param dto      입력될 날짜정보입니다.
     * @return list 날짜정보를 통해 수리스케줄들이 반한됩니다.
     * @author 유호철
     */
    @Override
    public Page<GetRepairScheduleResponseDto> findAllByDate(
        RepairScheduleRequestDto dto, Pageable pageable) {
        QRepairSchedule repairSchedule = QRepairSchedule.repairSchedule;
        QDayLabor dayLabor = QDayLabor.dayLabor;
        QAddressLocal addressLocal = QAddressLocal.addressLocal;

        QueryResults<GetRepairScheduleResponseDto> result = from(repairSchedule)
            .leftJoin(repairSchedule.dayLabor, dayLabor)
            .innerJoin(dayLabor.addressLocal, addressLocal)
            .where(repairSchedule.pk.date
                .between(dto.getStartDate(), dto.getEndDate()))
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

    /**
     * pk 값을 각자넣어서 반환받기위한 메서드.
     *
     * @param localNo 조회할 지역번호
     * @param date    조회할 일자
     * @return optional 수리스케줄을 optional 을 씌워서 반환합니다.
     * @author 유호철
     */
    @Override
    public Optional<RepairSchedule> findByPk(Integer localNo, LocalDate date) {
        QRepairSchedule schedule = QRepairSchedule.repairSchedule;

        return Optional.ofNullable(from(schedule)
            .where(schedule.pk.date.eq(date)
                .and(schedule.pk.addressNo.eq(localNo)))
            .select(schedule)
            .fetchOne());
    }
}
