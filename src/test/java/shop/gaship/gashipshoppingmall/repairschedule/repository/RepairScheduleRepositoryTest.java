package shop.gaship.gashipshoppingmall.repairschedule.repository;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.daylabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.daylabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.repairschedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairschedule.dummy.RepairScheduleDummy;
import shop.gaship.gashipshoppingmall.repairschedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairschedule.entity.pk.RepairSchedulePk;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.repository fileName
 * :RepairScheduleRepositoryTest author         : HoChul date           : 2022/07/09 description :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2022/07/09        HoChul 최초생성
 */
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

    @DisplayName("일자별 스케줄 조회")
    @Test
    void repairScheduleDate() {
        //given
        AddressLocal a1 = AddressLocalDummy.dummy1();
        AddressLocal a2 = AddressLocalDummy.dummy2();
        DayLabor d1 = DayLaboyDummy.dummy1();
        DayLabor d2 = DayLaboyDummy.dummy2();

        a1.registerDayLabor(d1);
        a2.registerDayLabor(d2);

        d1.fixLocation(a1);
        d2.fixLocation(a2);
        LocalDate now = LocalDate.now();
        RepairSchedulePk p1 = new RepairSchedulePk(now, d1.getAddressNo());
        RepairSchedulePk p2 = new RepairSchedulePk(now, d2.getAddressNo());

        RepairSchedule r2 = new RepairSchedule(p1, 10);
        r2.fixDayLabor(d2);
        RepairSchedule r1 = new RepairSchedule(p2, 20);
        r1.fixDayLabor(d1);
        //when
        addressLocalRepository.save(a1);
        addressLocalRepository.save(a2);
        dayLaborRepository.save(d1);
        dayLaborRepository.save(d2);
        repository.save(r1);
        repository.save(r2);
        //then
        List<GetRepairScheduleResponseDto> allByDate = repository.findAllByDate(now);

        assertThat(allByDate.get(0).getLocalDate()).isEqualTo(now);

    }

    @DisplayName("일자별 paging")
    @Test
    void date_Paging() {
        //given
        AddressLocal a1 = AddressLocalDummy.dummy1();
        AddressLocal a2 = AddressLocalDummy.dummy2();
        DayLabor d1 = DayLaboyDummy.dummy1();
        DayLabor d2 = DayLaboyDummy.dummy2();
        Pageable page = PageRequest.of(1, 10);

        a1.registerDayLabor(d1);
        a2.registerDayLabor(d2);

        d1.fixLocation(a1);
        d2.fixLocation(a2);
        LocalDate now = LocalDate.now();
        RepairSchedulePk p1 = new RepairSchedulePk(now, d1.getAddressNo());
        RepairSchedulePk p2 = new RepairSchedulePk(now, d2.getAddressNo());

        RepairSchedule r2 = new RepairSchedule(p1, 10);
        r2.fixDayLabor(d2);
        RepairSchedule r1 = new RepairSchedule(p2, 20);
        r1.fixDayLabor(d1);
        //when
        addressLocalRepository.save(a1);
        addressLocalRepository.save(a2);
        dayLaborRepository.save(d1);
        dayLaborRepository.save(d2);
        repository.save(r1);
        repository.save(r2);
        //then
        Page<GetRepairScheduleResponseDto> result = repository.findAllSortDate(page);

        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getSize()).isEqualTo(page.getPageSize());
    }

    @DisplayName("이미 있는 id 인지 찾기")
    @Test
    void findAddressNoAndDate() {
        //given
        AddressLocal a1 = AddressLocalDummy.dummy1();
        DayLabor d1 = DayLaboyDummy.dummy1();

        a1.registerDayLabor(d1);

        d1.fixLocation(a1);
        LocalDate now = LocalDate.now();
        RepairSchedulePk p1 = new RepairSchedulePk(now, d1.getAddressNo());
        RepairSchedule r1 = new RepairSchedule(p1, 10);
        r1.fixDayLabor(d1);

        //when
        addressLocalRepository.save(a1);
        dayLaborRepository.save(d1);
        repository.save(r1);

        //then
        RepairSchedule result = repository.findByPk(
            r1.pk.getAddressNo(), r1.pk.getDate()).get();

        assertThat(result.getDayLabor()).isEqualTo(d1);

    }
}
