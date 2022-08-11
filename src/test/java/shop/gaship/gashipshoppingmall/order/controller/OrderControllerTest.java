package shop.gaship.gashipshoppingmall.order.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderResponseDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.service.OrderService;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void doOrder() throws Exception {
        OrderRegisterRequestDto orderRegisterRequest = OrderDummy.createOrderRequestDtyDummy();

        ReflectionTestUtils.setField(orderRegisterRequest, "orderProducts",
            List.of(OrderProductDummy.dummy()));

        OrderResponseDto orderResponse = new OrderResponseDto(
            orderRegisterRequest.getTotalAmount(),
            1,
            "상품",
            orderRegisterRequest.getReceiverName()
        );
        given(orderService.insertOrder(any(OrderRegisterRequestDto.class)))
            .willReturn(1);
        given(orderService.findOrderForPayments(1))
            .willReturn(orderResponse);

        String content = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .writeValueAsString(orderRegisterRequest);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.amount").value(orderResponse.getAmount()))
            .andExpect(jsonPath("$.orderId").value(orderResponse.getOrderId()))
            .andExpect(jsonPath("$.orderName").value(orderResponse.getOrderName()))
            .andExpect(jsonPath("$.customerName").value(orderResponse.getCustomerName()))
            .andDo(print());
    }
}
