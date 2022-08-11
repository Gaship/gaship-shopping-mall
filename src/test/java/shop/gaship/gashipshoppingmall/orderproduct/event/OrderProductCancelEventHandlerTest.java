package shop.gaship.gashipshoppingmall.orderproduct.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import({OrderProductCancelEventHandler.class})
class OrderProductCancelEventHandlerTest {
    @Autowired
    private OrderProductCancelEventHandler orderProductCancelEventHandler;

    @MockBean
    private StatusCodeRepository statusCodeRepository;

    @MockBean
    private OrderProductService orderProductService;

    @Test
    @DisplayName("주문 상품을 주문 취소 상태로 만드는 이벤트 테스트입니다.")
    void updateOrderProductStatusToCancellationTest() {
        given(statusCodeRepository.findByStatusCodeName(OrderStatus.CANCEL_COMPLETE.getValue()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));
        willDoNothing().given(orderProductService)
            .updateOrderStatus(any(OrderProduct.class), any(StatusCode.class));

        OrderProduct dummy = OrderProductDummy.dummy();
        List<OrderProduct> orderProductDummies = IntStream.range(0, 10)
                .mapToObj(value -> dummy)
                .collect(Collectors.toUnmodifiableList());

        orderProductCancelEventHandler.updateOrderProductStatusToCancellation(
            new OrderProductCancelEvent(orderProductDummies));

        then(statusCodeRepository)
            .should(times(1))
            .findByStatusCodeName(OrderStatus.CANCEL_COMPLETE.getValue());

        then(orderProductService)
            .should(times(10))
            .updateOrderStatus(any(OrderProduct.class), any(StatusCode.class));
    }
}
