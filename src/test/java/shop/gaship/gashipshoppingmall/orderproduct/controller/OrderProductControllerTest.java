package shop.gaship.gashipshoppingmall.orderproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

@WebMvcTest(OrderProductController.class)
class OrderProductControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    OrderProductService orderProductService;

    @DisplayName("멤버 번호를 통해 상품을 조회")
    @Test
    void orderproductFindByMember() throws Exception {
        List<OrderProductResponseDto> list = new ArrayList<>();
        OrderProductResponseDto dto = new OrderProductResponseDto(1, 1, "test", 100L, LocalDateTime.now(), "유유", "010", "배송",
            "uuid");
        PageRequest pageRequest = PageRequest.of(0, 10);
        list.add(dto);
        PageImpl<OrderProductResponseDto> page = new PageImpl<>(list, pageRequest, 1);

        when(orderProductService.findMemberOrders(anyInt(), any()))
            .thenReturn(page);

        mvc.perform(get("/api/order-products/member/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(jsonPath("$.content.[0].orderProductNo").value(dto.getOrderProductNo()))
            .andExpect(jsonPath("$.content.[0].orderNo").value(dto.getOrderNo()))
            .andExpect(jsonPath("$.content.[0].productName").value(dto.getProductName()))
            .andExpect(jsonPath("$.content.[0].totalOrderAmount").value(dto.getTotalOrderAmount()))
            .andExpect(jsonPath("$.content.[0].receiptName").value(dto.getReceiptName()))
            .andExpect(jsonPath("$.content.[0].receiptPhoneNumber").value(dto.getReceiptPhoneNumber()))
            .andExpect(jsonPath("$.content.[0].orderStatus").value(dto.getOrderStatus()))
            .andExpect(jsonPath("$.content.[0].trackingNo").value(dto.getTrackingNo()))

            .andExpect(status().isOk())
            .andDo(print());
    }
}