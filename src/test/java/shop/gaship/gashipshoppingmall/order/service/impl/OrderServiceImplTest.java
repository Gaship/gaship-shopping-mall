package shop.gaship.gashipshoppingmall.order.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslist.dummy.AddressListDummy;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderDetailResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderListResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderResponseDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepository;
import shop.gaship.gashipshoppingmall.order.service.OrderService;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductRegisteredEvent;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductRegisteredEventHandler;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductRegisterEvent;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductRegisterEventHandler;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import({OrderServiceImpl.class})
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;

    @MockBean
    private OrderProductRegisterEventHandler handler;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private AddressListRepository addressListRepository;

    @Test
    @DisplayName("주문 등록 테스트")
    void insertOrderTest() {
        OrderRegisterRequestDto orderRequestDtoDummy = OrderDummy.createOrderRequestDtyDummy();
        Order orderDummy = OrderDummy.createOrderDummy();

        given(addressListRepository.findById(anyInt()))
            .willReturn(Optional.of(AddressListDummy.addressListEntity()));
        given(memberRepository.findById(anyInt())).willReturn(Optional.of(MemberDummy.dummy()));
        given(orderRepository.save(any(Order.class))).willReturn(orderDummy);
        willDoNothing().given(applicationEventPublisher)
            .publishEvent(any(OrderProductRegisterEvent.class));
        willDoNothing().given(handler)
            .saveOrderProduct(any(OrderProductRegisterEvent.class));

        orderService.insertOrder(orderRequestDtoDummy);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(addressListRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findById(any());
        verify(handler, times(1)).saveOrderProduct(any());
    }

    @Test
    @DisplayName("주문 등록 후 결제를 위한 주문조회 테스트")
    void findOrderForPayments() {
        Order orderDummy = OrderDummy.createOrderDummy();
        ReflectionTestUtils.setField(orderDummy, "orderProducts",
            List.of(OrderProductDummy.dummy()));

        given(orderRepository.findById(anyInt()))
            .willReturn(Optional.of(orderDummy));

        OrderResponseDto orderResponse = orderService.findOrderForPayments(1);
        assertThat(orderResponse.getOrderId())
            .isEqualTo(orderDummy.getNo());
        assertThat(orderResponse.getAmount())
            .isEqualTo(orderDummy.getTotalOrderAmount());
        assertThat(orderResponse.getCustomerName())
            .isEqualTo(orderDummy.getReceiptName());
        assertThat(orderResponse.getOrderName())
            .isEqualTo(ProductDummy.dummy().getName());

        verify(orderRepository, times(1)).findById(1);
    }

    @DisplayName("전체 주문조회")
    @Test
    void findOrdersAll() {
        Order order = OrderDummy.createOrderDummy();
        PageRequest pageRequest = PageRequest.of(0, 10);
        OrderListResponseDto dto = new OrderListResponseDto(1, "마산턱별시",
            LocalDateTime.now(), "유유", "010", "요청", 100L);
        PageImpl<OrderListResponseDto> page = new PageImpl<>(List.of(dto), pageRequest, 1);

        ReflectionTestUtils.setField(order, "orderProducts",
            List.of(OrderProductDummy.dummy()));
        given(memberRepository.findById(anyInt()))
            .willReturn(Optional.of(MemberDummy.dummy()));
        given(orderRepository.findAllOrders(anyInt(), any()))
            .willReturn(page);

        Page<OrderListResponseDto> result = orderService.findAllMemberOrders(1, pageRequest);
        assertThat(result.getContent().get(0).getOrderNo()).isEqualTo(dto.getOrderNo());
        assertThat(result.getContent().get(0).getOrderDatetime().getYear()).isEqualTo(dto.getOrderDatetime().getYear());
        assertThat(result.getContent().get(0).getAddress()).isEqualTo(dto.getAddress());
        assertThat(result.getContent().get(0).getTotalOrderAmount()).isEqualTo(dto.getTotalOrderAmount());
        assertThat(result.getContent().get(0).getReceiptName()).isEqualTo(dto.getReceiptName());
        assertThat(result.getContent().get(0).getDeliveryRequest()).isEqualTo(dto.getDeliveryRequest());
        assertThat(result.getContent().get(0).getReceiptPhoneNumber()).isEqualTo(dto.getReceiptPhoneNumber());
    }

    @DisplayName("실패된 주문들조회")
    @Test
    void findCancelOrdersTest() {
        OrderCancelResponseDto dto =
            new OrderCancelResponseDto(1, "aa", LocalDateTime.now(), "호호처리", "011",
                "배송", 1000L, 1, 1L, "취소", LocalDateTime.now().plusYears(2));
        PageImpl<OrderCancelResponseDto> page = new PageImpl<>(List.of(dto));
        given(memberRepository.findById(anyInt()))
            .willReturn(Optional.of(MemberDummy.dummy()));
        given(orderRepository.findCancelOrders(anyInt(), anyString(), any(Pageable.class)))
            .willReturn(page);

        Page<OrderCancelResponseDto> result = orderService.findMemberCancelOrders(1, "취소", PageRequest.of(0, 10));

        assertThat(result.getContent().get(0).getAddress()).isEqualTo(dto.getAddress());
        assertThat(result.getContent().get(0).getOrderNo()).isEqualTo(dto.getOrderNo());
        assertThat(result.getContent().get(0).getOrderDatetime()).isEqualTo(dto.getOrderDatetime());
        assertThat(result.getContent().get(0).getReceiptName()).isEqualTo(dto.getReceiptName());
        assertThat(result.getContent().get(0).getReceiptPhoneNumber()).isEqualTo(dto.getReceiptPhoneNumber());
        assertThat(result.getContent().get(0).getDeliveryRequest()).isEqualTo(dto.getDeliveryRequest());
        assertThat(result.getContent().get(0).getOrderProductNo()).isEqualTo(dto.getOrderProductNo());
        assertThat(result.getContent().get(0).getCancellationAmount()).isEqualTo(dto.getCancellationAmount());
        assertThat(result.getContent().get(0).getCancellationReason()).isEqualTo(dto.getCancellationReason());
        assertThat(result.getContent().get(0).getCancellationDatetime()).isEqualTo(dto.getCancellationDatetime());
    }

    @DisplayName("주문 상세 내용 조회")
    @Test
    void findOrderDetailsTest() {
        OrderDetailResponseDto dto =
            new OrderDetailResponseDto("status", LocalDateTime.now(), "Hochul", "00",
                "집앞에", 100L, "마산", "11105", 10L, 1, LocalDate.now());
        PageImpl<OrderDetailResponseDto> page = new PageImpl<>(List.of(dto), PageRequest.of(1, 10), 1);
        given(memberRepository.findById(anyInt()))
            .willReturn(Optional.of(MemberDummy.dummy()));
        given(orderRepository.findById(anyInt()))
            .willReturn(Optional.of(OrderDummy.createOrderDummy()));
        given(orderRepository.findOrderDetails(anyInt(), anyInt(), any(Pageable.class)))
            .willReturn(page);

        Page<OrderDetailResponseDto> result = orderService.findMemberOrderDetails(1, 1, PageRequest.of(1, 10));

        assertThat(result.getContent().get(0).getAddress()).isEqualTo(dto.getAddress());
        assertThat(result.getContent().get(0).getOrderDatetime()).isEqualTo(dto.getOrderDatetime());
        assertThat(result.getContent().get(0).getAmount()).isEqualTo(dto.getAmount());
        assertThat(result.getContent().get(0).getReceiptName()).isEqualTo(dto.getReceiptName());
        assertThat(result.getContent().get(0).getReceiptPhoneNumber()).isEqualTo(dto.getReceiptPhoneNumber());
        assertThat(result.getContent().get(0).getDeliveryRequest()).isEqualTo(dto.getDeliveryRequest());
        assertThat(result.getContent().get(0).getTotalOrderAmount()).isEqualTo(dto.getTotalOrderAmount());
        assertThat(result.getContent().get(0).getZipCode()).isEqualTo(dto.getZipCode());
        assertThat(result.getContent().get(0).getTrackingNo()).isEqualTo(dto.getTrackingNo());
        assertThat(result.getContent().get(0).getHopeDate()).isEqualTo(dto.getHopeDate());
    }
}
