package shop.gaship.gashipshoppingmall.orderproduct.event;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderProductRegisterEventHandler.class)
class OrderProductRegisterEventHandlerTest {
    @Autowired
    private OrderProductRegisterEventHandler orderProductRegisterEventHandler;

    @MockBean
    private OrderProductService orderProductService;

    @Test
    @DisplayName("주문의 저장이 완료 시 주문상품의 저장을 위한 이벤트입니다. ")
    void saveOrderProduct() {
        Order orderDummy = OrderDummy.createOrderDummy();
        OrderProductSpecificDto orderProductSpecificDto = new OrderProductSpecificDto();
        ReflectionTestUtils.setField(orderProductSpecificDto, "productNo", 1);
        ReflectionTestUtils.setField(orderProductSpecificDto, "amount", 10000000L);
        ReflectionTestUtils.setField(orderProductSpecificDto, "additionalWarrantyPeriod", 1);
        ReflectionTestUtils.setField(orderProductSpecificDto, "couponNo", null);
        ReflectionTestUtils.setField(orderProductSpecificDto, "couponAmount", 100000L);
        ReflectionTestUtils.setField(orderProductSpecificDto, "hopeDate", null);

        List<OrderProductSpecificDto> orderProductSpecificDtos = IntStream.range(0, 10)
            .mapToObj(value -> orderProductSpecificDto)
            .collect(Collectors.toUnmodifiableList());

        OrderProductRegisterEvent event =
            new OrderProductRegisterEvent(orderDummy, orderProductSpecificDtos);

        willDoNothing()
            .given(orderProductService)
            .registerOrderProduct(orderDummy, orderProductSpecificDtos);

        orderProductRegisterEventHandler.saveOrderProduct(event);

        then(orderProductService)
            .should(times(1))
            .registerOrderProduct(orderDummy, orderProductSpecificDtos);
    }
}
