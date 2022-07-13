package shop.gaship.gashipshoppingmall.repairSechedule.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.repairSechedule.dummy.RepairScheduleDummy;
import shop.gaship.gashipshoppingmall.repairSechedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairSechedule.entity.pk.RepairSchedulePk;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.repository
 * fileName       :RepairScheduleRepositoryTest
 * author         : HoChul
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR NOTE
 * -----------------------------------------------------------
 * 2022/07/09        HoChul 최초생성
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class RepairScheduleRepositoryTest {

    @Autowired
    RepairScheduleRepository repository;

    @Autowired
    AddressLocalRepository addressLocalRepository;

    @Autowired
    DayLaborRepository dayLaborRepository;

    @DisplayName("조회를 위한 테스트")
    @Test
    void repairScheduleSelectTest() {
        //given
        AddressLocal upper = AddressLocalDummy.dummy1();
        DayLabor labor = DayLaboyDummy.dummy1();
        labor.fixLocation(upper);

        RepairSchedule repairSchedule = RepairScheduleDummy.dummy();
        repairSchedule.fixDayLabor(labor);

        RepairSchedulePk pk = repairSchedule.getPk();

        //when & then
        addressLocalRepository.save(upper);
        dayLaborRepository.save(labor);
        repository.save(repairSchedule);
        RepairSchedule schedule = repository.findById(pk).get();

        assertThat(schedule.getDayLabor()).isEqualTo(repairSchedule.getDayLabor());
        assertThat(schedule.getLabor()).isEqualTo(repairSchedule.getLabor());
        assertThat(schedule.getPk()).hasSameHashCodeAs(pk);
        assertThat(schedule.getPk().getAddressNo()).isEqualTo(pk.getAddressNo());
        assertThat(schedule.getPk().getDate()).isEqualTo(pk.getDate());
        assertThat(schedule.getPk()).isEqualTo(pk);
    }
}