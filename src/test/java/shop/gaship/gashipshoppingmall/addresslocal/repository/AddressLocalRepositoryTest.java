package shop.gaship.gashipshoppingmall.addresslocal.repository;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressSubLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressUpperLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.daylabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.daylabor.repository.DayLaborRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AddressLocalRepositoryTest {
    @Autowired
    AddressLocalRepository repository;

    @Autowired
    DayLaborRepository laborRepository;

    DayLabor labor;

    AddressLocal upper;
    AddressLocal child1;
    AddressLocal child2;

    @BeforeEach
    void setUp() {
        upper = AddressLocalDummy.dummy1();
        child1 = AddressLocalDummy.dummy2();
        child2 = AddressLocalDummy.dummy3();
        labor = DayLaboyDummy.dummy1();
    }

    @DisplayName("조회 되는지 확인용 테스트")
    @Test
    void selectTest() {
        //given
        labor.fixLocation(upper);

        upper.getSubLocal().add(child1);
        upper.getSubLocal().add(child2);
        upper.registerDayLabor(labor);

        child1.registerUpperLocal(upper);
        child2.registerUpperLocal(upper);

        //when
        laborRepository.save(labor);
        repository.save(upper);
        repository.save(child1);
        repository.save(child2);

        AddressLocal test = repository.findById(upper.getAddressNo()).get();
        repository.findAllAddress();

        assertThat(repository.findByLevel(2)).hasSize(2);
        assertThat(repository.findByLevel(1).get(0)).isEqualTo(upper);
        assertThat(upper.getSubLocal()).hasSize(2);
        assertThat(test.getAddressName()).isEqualTo(upper.getAddressName());
        assertThat(test.getLevel()).isEqualTo(upper.getLevel());
        assertThat(test.isAllowDelivery()).isEqualTo(upper.isAllowDelivery());
        assertThat(test.getDayLabor()).isEqualTo(labor);
        assertThat(child1.getUpperLocal().getAddressName()).isEqualTo(upper.getAddressName());
    }

    @DisplayName("전체 조회 테스트")
    @Test
    void findAll() {
        List<AddressLocal> list = new ArrayList<>();
        list.add(child1);

        labor.fixLocation(upper);
        upper.registerDayLabor(labor);
        upper.updateSubLocal(list);

        child1.registerUpperLocal(upper);

        //when
        laborRepository.save(labor);
        repository.save(upper);
        repository.save(child1);

        List<AddressUpperLocalResponseDto> result = repository.findAllAddress();
        assertThat(result.get(0).getAddressName()).isEqualTo(upper.getAddressName());
        assertThat(result.get(0).getAddressNo()).isEqualTo(upper.getAddressNo());
        assertThat(result.get(0).isAllowDelivery()).isEqualTo(upper.isAllowDelivery());
    }

    @DisplayName("지역 검색시 관련 하위 지역들 나오는지 테스트")
    @Test
    void address_searchTest() {
        //given
        List<AddressLocal> list = new ArrayList<>();
        list.add(child1);
        list.add(child2);

        labor.fixLocation(upper);
        upper.registerDayLabor(labor);
        upper.updateSubLocal(list);

        child1.registerUpperLocal(upper);
        child2.registerUpperLocal(upper);

        //when
        laborRepository.save(labor);
        repository.save(upper);
        repository.save(child1);
        repository.save(child2);

        List<AddressSubLocalResponseDto> result = repository.findSubAddress(upper.getAddressName());
        assertThat(result.get(0).getAddressName()).isEqualTo(child1.getAddressName());
        assertThat(result.get(0).getAddressNo()).isEqualTo(child1.getAddressNo());
        assertThat(result.get(1).getAddressName()).isEqualTo(child2.getAddressName());
        assertThat(result.get(1).getAddressNo()).isEqualTo(child2.getAddressNo());

    }

}