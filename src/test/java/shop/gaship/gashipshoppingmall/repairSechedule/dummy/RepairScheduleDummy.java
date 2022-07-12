package shop.gaship.gashipshoppingmall.repairSechedule.dummy;

import java.time.LocalDate;
import shop.gaship.gashipshoppingmall.repairSechedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairSechedule.entity.pk.RepairSchedulePk;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.dummy fileName       :
 * RepairSecheduleDummy author         : 유호철 date           : 2022/07/12 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/12        유호철       최초
 * 생성
 */
public class RepairScheduleDummy {
    private RepairScheduleDummy(){

    }

    public static RepairSchedule dummy(){

        return new RepairSchedule(new RepairSchedulePk(LocalDate.now(), 1),10);
    }

}
