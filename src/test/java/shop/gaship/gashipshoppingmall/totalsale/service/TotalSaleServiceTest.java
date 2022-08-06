package shop.gaship.gashipshoppingmall.totalsale.service;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;
import shop.gaship.gashipshoppingmall.totalsale.exception.LocalDateMaxYearException;
import shop.gaship.gashipshoppingmall.totalsale.service.impl.TotalSaleServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(TotalSaleServiceImpl.class)
class TotalSaleServiceTest {

    @Autowired
    TotalSaleService service;

    @MockBean
    OrderProductRepository repository;

    @DisplayName("날짜를 통해 매출현황 조회시 1년이 넘어서 실패할경우")
    @Test
    void WrongYearException() {
        //given
        TotalSaleRequestDto dto = new TotalSaleRequestDto(LocalDateTime.now(), LocalDateTime.now().plusYears(2));
        //when
        assertThatThrownBy(() -> service.findTotalSaleList(dto))
            .isInstanceOf(LocalDateMaxYearException.class);
    }

    @DisplayName("날짜를 통해 매출현황 조회시")
    @Test
    void findTotalSaleListTest() {
        //given
        LocalDateTime startDate = LocalDateTime.of(2022, 2, 2, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 12, 30, 23, 59, 59);

        TotalSaleRequestDto dto = new TotalSaleRequestDto(startDate, endDate);
        TotalSaleResponseDto responseDto = new TotalSaleResponseDto(startDate, 2L, 1L, 1L, 10000L, 1000, 9000L);

        //when
        given(repository.findTotalSale(dto))
            .willReturn(List.of(responseDto));

        List<TotalSaleResponseDto> result = service.findTotalSaleList(dto);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTotalSaleDate()).isEqualTo(startDate);
        assertThat(result.get(0).getCancelAmount()).isEqualTo(responseDto.getCancelAmount());
        assertThat(result.get(0).getTotalAmount()).isEqualTo(responseDto.getTotalAmount());
        assertThat(result.get(0).getOrderSaleAmount()).isEqualTo(responseDto.getOrderSaleAmount());
        assertThat(result.get(0).getOrderCancelCnt()).isEqualTo(responseDto.getOrderCancelCnt());
        assertThat(result.get(0).getOrderCnt()).isEqualTo(responseDto.getOrderCnt());
    }
}