package shop.gaship.gashipshoppingmall.repairSchedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.pk.RepairSchedulePk;
import shop.gaship.gashipshoppingmall.repairSchedule.repository.custom.RepairScheduleRepositoryCustom;


/**
 * 수리스케줄을 다루기위한 레포지토리 인터페이스 입니다.
 * JPA 를 사용하고 QueryDsl 을 사용합니다.
 *
 * @see JpaRepository
 * @see RepairScheduleRepositoryCustom
 * @author : 유호철
 * @since 1.0
 */
public interface RepairScheduleRepository extends JpaRepository<RepairSchedule, RepairSchedulePk>,
        RepairScheduleRepositoryCustom {

}
