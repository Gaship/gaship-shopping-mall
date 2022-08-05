package shop.gaship.gashipshoppingmall.orderproduct.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import shop.gaship.gashipshoppingmall.config.DataSourceConfig;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleDto;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, DataProtectionConfig.class})
class OrderProductRepositoryTest {
    @Autowired
    OrderProductRepository orderProductRepository;

    @Test
    void test1() {
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 12, 30, 23, 59, 59);
        TotalSaleRequestDto requestDto = new TotalSaleRequestDto(startDate, endDate);
        List<TotalSaleDto> totalSale = orderProductRepository.findTotalSale(requestDto);
        assertThat(totalSale).hasSize(3);
    }
}