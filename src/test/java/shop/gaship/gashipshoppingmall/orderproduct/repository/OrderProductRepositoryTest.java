package shop.gaship.gashipshoppingmall.orderproduct.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import shop.gaship.gashipshoppingmall.config.DataSourceConfig;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;

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

    @DisplayName("mysql 에서 특정범위의 매출량을 보기위한 테스트입니다.")
    @Test
    void findTotalSaleTestBetweenStartDateEndDate() {
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 12, 30, 23, 59, 59);
        TotalSaleRequestDto requestDto = new TotalSaleRequestDto(startDate, endDate);
        List<TotalSaleResponseDto> totalSale = orderProductRepository.findTotalSale(requestDto);
        assertThat(totalSale).hasSize(3);
    }
}