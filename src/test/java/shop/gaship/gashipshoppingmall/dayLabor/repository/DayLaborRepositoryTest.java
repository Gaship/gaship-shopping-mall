package shop.gaship.gashipshoppingmall.dayLabor.repository;

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

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.repository fileName       :
 * DayLaborRepositoryTest
 * author         : HoChul
 * date           : 2022/07/09
 * description    :
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

    @DisplayName("조회를 위한 테스트")
    @Test
    void selectTestDayLabor() {
        AddressLocal upper = AddressLocalDummy.dummy1();
        DayLabor dayLabor = DayLaboyDummy.dummy();
        dayLabor.fixLocation(upper);

        addressLocalRepository.save(upper);
        repository.save(dayLabor);

        assertThat(repository.findById(dayLabor.getAddressNo())).contains(dayLabor);
        assertThat(dayLabor.getAddressLocal()).isEqualTo(upper);
    }
}