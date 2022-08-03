package shop.gaship.gashipshoppingmall.order.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addresslist.dummy.AddressListDummy;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepository;
import shop.gaship.gashipshoppingmall.order.service.OrderService;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderServiceImpl.class)
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderProductService orderProductService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private AddressListRepository addressListRepository;

    @Test
    void insertOrderTest() {
        OrderRegisterRequestDto orderRequestDtoDummy = OrderDummy.createOrderRequestDtyDummy();
        Order orderDummy = OrderDummy.createOrderDummy();

        given(addressListRepository.findById(anyInt()))
            .willReturn(Optional.of(AddressListDummy.addressListEntity()));
        given(memberRepository.findById(anyInt())).willReturn(Optional.of(MemberDummy.dummy()));
        given(orderRepository.save(any(Order.class))).willReturn(orderDummy);
        willDoNothing().given(orderProductService).registerOrderProduct(any(Order.class), anyList());

        orderService.insertOrder(orderRequestDtoDummy);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(addressListRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findById(any());
    }
}
