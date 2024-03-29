package shop.gaship.gashipshoppingmall.repairschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.repairschedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairschedule.entity.pk.RepairSchedulePk;
import shop.gaship.gashipshoppingmall.repairschedule.repository.custom.RepairScheduleRepositoryCustom;


/**
 * 수리스케줄을 다루기위한 레포지토리 인터페이스 입니다.
 * JPA 를 사용하고 QueryDsl 을 사용합니다.
 *
 * @author : 유호철
 * @see JpaRepository
 * @see RepairScheduleRepositoryCustom
 * @since 1.0
 */
public interface RepairScheduleRepository
    extends JpaRepository<RepairSchedule, RepairSchedulePk>, RepairScheduleRepositoryCustom {

}
