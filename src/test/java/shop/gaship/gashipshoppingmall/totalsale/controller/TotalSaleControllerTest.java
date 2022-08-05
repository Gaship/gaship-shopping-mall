package shop.gaship.gashipshoppingmall.totalsale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;
import shop.gaship.gashipshoppingmall.totalsale.service.TotalSaleService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@Slf4j
@WebMvcTest({TotalSaleController.class})
class TotalSaleControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TotalSaleService service;

    @Autowired
    ObjectMapper objectMapper;


    @DisplayName("매출현황 컨트롤러 테스트")
    @Test
    void totalSaleTest() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 12, 30, 23, 59, 59);

        TotalSaleRequestDto dto = new TotalSaleRequestDto(startDate, endDate);
        TotalSaleResponseDto responseDto = new TotalSaleResponseDto(startDate, 2L, 1L, 1L, 10000L, 1000, 9000L);

        //when
        when(service.findTotalSaleList(any())).thenReturn(List.of(responseDto));

        mvc.perform(get("/api/total-sale/date/{start}/end/{end}", startDate, endDate)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            // 날짜비교는 안됨..

            .andExpect(jsonPath("$[0].orderCnt").value(objectMapper.writeValueAsString(responseDto.getOrderCnt())))
            .andExpect(jsonPath("$[0].orderCancelCnt").value(objectMapper.writeValueAsString(responseDto.getOrderCancelCnt())))
            .andExpect(jsonPath("$[0].orderSaleCnt").value(objectMapper.writeValueAsString(responseDto.getOrderSaleCnt())))
            .andExpect(jsonPath("$[0].totalAmount").value(objectMapper.writeValueAsString(responseDto.getTotalAmount())))
            .andExpect(jsonPath("$[0].cancelAmount").value(objectMapper.writeValueAsString(responseDto.getCancelAmount())))
            .andExpect(jsonPath("$[0].orderSaleAmount").value(objectMapper.writeValueAsString(responseDto.getOrderSaleAmount())))
            .andDo(print());

        verify(service, times(1)).findTotalSaleList(any());

    }
}