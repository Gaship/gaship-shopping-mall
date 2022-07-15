package shop.gaship.gashipshoppingmall.repairSchedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.pk.RepairSchedulePk;
import shop.gaship.gashipshoppingmall.repairSchedule.repository.custom.RepairScheduleRepositoryCustom;

import java.time.LocalDate;
import java.util.Optional;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.repository
 * fileName       : RepairScheduleRepository
 * author         : HoChul
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/09        HoChul     최초 생성
 */
public interface RepairScheduleRepository extends JpaRepository<RepairSchedule, RepairSchedulePk>,
        RepairScheduleRepositoryCustom {

    Optional<RepairSchedule> findByPk_AddressNoAndPk_Date(Integer localNo, LocalDate date);

}
