package shop.gaship.gashipshoppingmall.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderSuccessRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderResponseDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.service.OrderService;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductCancellationFailDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductStatusCancelDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductStatusChangeDto;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private OrderProductService orderProductService;
    @MockBean
    private OrderService orderService;

    private final PageRequest pageRequest = PageRequest.of(0, 10);

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

    @DisplayName("주문번호, 회원번호, 상태값을 통해 취소주문 확인 테스트")
    @Test
    void orderCancelList() throws Exception {
        OrderCancelResponseDto dto =
            new OrderCancelResponseDto(1, "aa", LocalDateTime.now(), "호호처리", "011",
                "배송", 1000L, 1, 1L, "취소", LocalDateTime.now().plusYears(2));
        PageImpl<OrderCancelResponseDto> page = new PageImpl<>(List.of(dto), pageRequest, 1);
        when(orderService.findMemberCancelOrders(anyInt(), any(String.class), any()))
            .thenReturn(page);

        mockMvc.perform(get("/api/orders/member/{memberNo}/status/{status}", 1, "배송")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.[0].orderNo").value(dto.getOrderNo()))
            .andExpect(jsonPath("$.content.[0].address").value(dto.getAddress()))
            .andExpect(jsonPath("$.content.[0].receiptName").value(dto.getReceiptName()))
            .andExpect(jsonPath("$.content.[0].receiptPhoneNumber").value(dto.getReceiptPhoneNumber()))
            .andExpect(jsonPath("$.content.[0].deliveryRequest").value(dto.getDeliveryRequest()))
            .andExpect(jsonPath("$.content.[0].cancellationReason").value(dto.getCancellationReason()))
            .andExpect(jsonPath("$.content.[0].cancellationAmount").value(dto.getCancellationAmount()))

            .andDo(print());
    }


    @Test
    void orderSuccess() throws Exception {
        OrderSuccessRequestDto orderSuccessRequestDto = new OrderSuccessRequestDto();
        ReflectionTestUtils.setField(orderSuccessRequestDto, "orderNo", 1);
        ReflectionTestUtils.setField(orderSuccessRequestDto, "paymentKey", "1234");

        willDoNothing()
            .given(orderService)
            .orderPaymentsSuccess(anyInt(), anyString());

        mockMvc.perform(put("/api/orders/success")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderSuccessRequestDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void orderCancelRefundProduct() throws Exception {
        OrderProductStatusCancelDto orderProductStatusCancelDto = new OrderProductStatusCancelDto(
            1,
            IntStream.range(0, 5).mapToObj(value ->
                    new OrderProductStatusCancelDto.CancelOrderInfo(
                        value,
                        10000L))
                .collect(Collectors.toUnmodifiableList()),
            "단순 변심으로 인한 주문 취소"
        );

        willDoNothing()
            .given(orderProductService)
            .updateOrderProductStatusToCancel(any(OrderProductStatusCancelDto.class));

        mockMvc.perform(put("/api/orders/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderProductStatusCancelDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void orderChangeProduct() throws Exception {
        OrderProductStatusChangeDto orderProductStatusChangeDto = new OrderProductStatusChangeDto();
        ReflectionTestUtils.setField(orderProductStatusChangeDto, "orderProductNos", List.of(1, 2, 3, 4, 5));

        willDoNothing()
            .given(orderProductService)
            .updateOrderProductStatusToChange(any(OrderProductStatusChangeDto.class));

        mockMvc.perform(put("/api/orders/change")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderProductStatusChangeDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void orderRestoreProduct() throws Exception {
        OrderProductCancellationFailDto orderProductCancellationFailDto = new OrderProductCancellationFailDto();
        ReflectionTestUtils.setField(orderProductCancellationFailDto, "paymentCancelHistoryNo", 1);
        ReflectionTestUtils.setField(orderProductCancellationFailDto, "restoreOrderProductNos", List.of(1, 2, 3, 4, 5));

        willDoNothing()
            .given(orderProductService)
            .updateOrderProductStatusToChange(any(OrderProductStatusChangeDto.class));

        mockMvc.perform(put("/api/orders/restore")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderProductCancellationFailDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }
}
