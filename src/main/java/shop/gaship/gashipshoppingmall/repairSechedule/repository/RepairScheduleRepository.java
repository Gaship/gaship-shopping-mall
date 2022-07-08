package shop.gaship.gashipshoppingmall.repairSechedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.repairSechedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairSechedule.entity.pk.RepairSchedulePk;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.repository fileName       :
 * RepairScheduleRepository author         : HoChul date           : 2022/07/09 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/09        HoChul
 * 최초 생성
 */
public interface RepairScheduleRepository extends JpaRepository<RepairSchedule, RepairSchedulePk> {

}
