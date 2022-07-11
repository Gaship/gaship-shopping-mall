package shop.gaship.gashipshoppingmall.repairSechedule.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.DayLaborRepository;
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
        AddressLocal upper = new AddressLocal(null,"무슨특별시",1,true);
        DayLabor labor = new DayLabor(upper.getAddressNo(),10);
        labor.setAddressLocal(upper);

        RepairSchedule repairSchedule = new RepairSchedule();
        RepairSchedulePk pk = new RepairSchedulePk(LocalDate.now(), labor.getAddressNo());

        repairSchedule.setPk(pk);
        repairSchedule.setDayLabor(labor);
        repairSchedule.setLabor(labor.getMaxLabor());

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
        assertThat(schedule.getPk().equals(pk)).isTrue();
    }
}