package shop.gaship.gashipshoppingmall.addressLocal.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.repository
 * fileName       :
 * AddressLocalRepositoryTest
 * author         : HoChul
 * date           : 2022/07/09
 * description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/09        HoChul
 * 최초 생성
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class AddressLocalRepositoryTest {
    @Autowired
    private AddressLocalRepository repository;

    private DayLabor labor;

    @BeforeEach
    void setUp() {
        labor = new DayLabor(1 , 10);
    }

    @DisplayName("조회 되는지 확인용 테스트")
    @Test
    void selectTest() {
        DayLabor labor = new DayLabor();

        AddressLocal upper = new AddressLocal(null,"무슨특별시",1,true);
        AddressLocal child1 = new AddressLocal(null,"무슨무슨1",2,true);
        AddressLocal child2 = new AddressLocal(null, "무슨무슨2", 2, true);

        upper.setSubLocal(List.of(child1,child2));
        child1.setUpperLocal(upper);
        child2.setUpperLocal(upper);

        repository.save(upper);
        repository.save(child1);
        repository.save(child2);

        repository.findById(upper.getAddressNo());
        repository.findAll();

        System.out.println(upper.getAddressNo());
        System.out.println(child1.getAddressNo());
        System.out.println(child2.getAddressNo());

        assertThat(repository.findByLevel(2)).hasSize(2);
        assertThat(repository.findByLevel(1).get(0)).isEqualTo(upper);
    }
}