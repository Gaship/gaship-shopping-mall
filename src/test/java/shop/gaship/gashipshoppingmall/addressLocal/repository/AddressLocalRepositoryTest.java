package shop.gaship.gashipshoppingmall.addressLocal.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.DayLaborRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AddressLocalRepositoryTest {
    @Autowired
    AddressLocalRepository repository;

    @Autowired
    DayLaborRepository laborRepository;

    DayLabor labor;

    @BeforeEach
    void setUp() {
        labor = new DayLabor(1 , 10);
    }

    @DisplayName("조회 되는지 확인용 테스트")
    @Test
    void selectTest() {
        DayLabor labor = DayLaboyDummy.dummy1();

        AddressLocal upper = AddressLocalDummy.dummy1();

        AddressLocal child1 = AddressLocalDummy.dummy2();
        AddressLocal child2 = AddressLocalDummy.dummy3();

        List<AddressLocal> list = new ArrayList<>();
        list.add(child1);
        list.add(child2);

        labor.fixLocation(upper);
        upper.registerDayLabor(labor);
        upper.addSubLocal(list);

        child1.registerUpperLocal(upper);
        child2.registerUpperLocal(upper);


        laborRepository.save(labor);
        repository.save(upper);
        repository.save(child1);
        repository.save(child2);

        AddressLocal test = repository.findById(upper.getAddressNo()).get();
        repository.findAll();

        assertThat(repository.findByLevel(2)).hasSize(2);
        assertThat(repository.findByLevel(1).get(0)).isEqualTo(upper);
        assertThat(upper.getSubLocal()).hasSize(2);
        assertThat(test.getAddressName()).isEqualTo(upper.getAddressName());
        assertThat(test.getLevel()).isEqualTo(upper.getLevel());
        assertThat(test.isAllowDelivery()).isEqualTo(upper.isAllowDelivery());
        assertThat(test.getDayLabor()).isEqualTo(labor);
        assertThat(child1.getUpperLocal().getAddressName()).isEqualTo(upper.getAddressName());
    }
}