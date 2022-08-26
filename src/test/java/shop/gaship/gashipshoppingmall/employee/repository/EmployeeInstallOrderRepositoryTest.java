package shop.gaship.gashipshoppingmall.employee.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import shop.gaship.gashipshoppingmall.config.DataSourceConfig;
import shop.gaship.gashipshoppingmall.order.entity.Order;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataProtectionConfig.class, DataSourceConfig.class})
class EmployeeInstallOrderRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @DisplayName("직원이 자신의 지역에서 설치해야 할 주문들을 조회합니다.")
    void findOrderBasedOnMyLocationTest() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Order> result = employeeRepository.findOrderBasedOnEmployeeLocation(pageable, 1);

        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.hasNext()).isFalse();
        assertThat(result.hasPrevious()).isFalse();
        assertThat(result.getContent()).isNotEmpty();
        System.out.println(result.getContent());
    }
}
