package shop.gaship.gashipshoppingmall.order.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import shop.gaship.gashipshoppingmall.config.DataSourceConfig;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = {DataSourceConfig.class, DataProtectionConfig.class})
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;

    PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 10);
    }


    @DisplayName("member no 를 통해서 취소된 것들을 조회")
    @Test
    void findAllCancelOrders() {
        orderRepository.findCancelOrders(1, "취소완료", pageRequest);
    }

}