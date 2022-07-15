package shop.gaship.gashipshoppingmall.dayLabor.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.config.dayLabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.config.dayLabor.entity.DayLabor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.repository fileName       :
 * DayLaborRepositoryTest
 * author         : HoChul
 * date           : 2022/07/09
 * description    : 지역별 물량 테이블클래스를 테스트하기위한 클래스
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/09        HoChul
 * 최초 생성
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest
class DayLaborRepositoryTest {

    @Autowired
    AddressLocalRepository addressLocalRepository;
    @Autowired
    DayLaborRepository repository;
    AddressLocal local;


    @BeforeEach
    void setUp() {
        local = AddressLocalDummy.dummy1();
    }

    @DisplayName("단건 조회를 위한 테스트")
    @Test
    void selectTestDayLabor() {
        //given
        DayLabor dayLabor = DayLaboyDummy.dummy1();
        dayLabor.fixLocation(local);

        //when & then
        addressLocalRepository.save(local);
        repository.save(dayLabor);

        assertThat(repository.findById(dayLabor.getAddressNo())).contains(dayLabor);
        assertThat(dayLabor.getAddressLocal()).isEqualTo(local);
    }

    @DisplayName("전체 조회를 위한 테스트")
    @Test
    void selectAllDayLabor() {
        //given
        DayLabor l1 = DayLaboyDummy.dummy1();
        DayLabor l2 = DayLaboyDummy.dummy2();
        AddressLocal addressLocal = AddressLocalDummy.dummy2();

        //when & then
        l1.fixLocation(local);
        l2.fixLocation(addressLocal);

        addressLocalRepository.save(local);
        addressLocalRepository.save(addressLocal);

        repository.save(l1);
        repository.save(l2);

        assertThat(repository.findAll()).hasSize(2);
    }

}