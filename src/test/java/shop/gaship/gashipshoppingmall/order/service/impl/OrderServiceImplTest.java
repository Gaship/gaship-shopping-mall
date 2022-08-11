package shop.gaship.gashipshoppingmall.order.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslist.dummy.AddressListDummy;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderResponseDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepository;
import shop.gaship.gashipshoppingmall.order.service.OrderService;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductRegisterEvent;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductRegisterEventHandler;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.event.ProductSaveUpdateEvent;

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

}
