package shop.gaship.gashipshoppingmall.orderproduct.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.service.decorator.impl.OrderProductRegistrationBaseDecorator;
import shop.gaship.gashipshoppingmall.orderproduct.service.decorator.impl.OrderProductRegistrationCouponDecorator;
import shop.gaship.gashipshoppingmall.orderproduct.service.impl.OrderProductServiceImpl;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import({OrderProductServiceImpl.class})
class OrderProductServiceTest {
    @Autowired
    private OrderProductService productService;

    @MockBean
    @Qualifier("orderProductBaseDecorator")
    private OrderProductRegistrationBaseDecorator orderProductBaseDecorator;

    @MockBean
    @Qualifier("orderProductCouponDecorator")
    private OrderProductRegistrationCouponDecorator orderProductCouponDecorator;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private StatusCodeRepository statusCodeRepository;

    @Test
    @DisplayName("주문 상품 등록")
    void registerOrderProduct() {
        Order order = OrderDummy.createOrderDummy();
        List<OrderProductSpecificDto> orderProductSpecifics = new ArrayList<>();
        OrderProductSpecificDto orderProductSpecific = new OrderProductSpecificDto();
        ReflectionTestUtils.setField(orderProductSpecific, "productNo", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "couponNo", null);
        ReflectionTestUtils.setField(orderProductSpecific, "hopeDate", null);

        IntStream.range(0,10).forEach(operand -> orderProductSpecifics.add(orderProductSpecific));

        Product productDummy = ProductDummy.dummy();

        given(statusCodeRepository.findByStatusCodeName(any()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));
        given(productRepository.findById(anyInt()))
            .willReturn(Optional.of(productDummy));
        given(orderProductBaseDecorator.save(any(OrderProduct.class)))
            .willReturn(OrderProductDummy.dummy());
        willDoNothing().given(orderProductBaseDecorator).cleanUpStock(productDummy);

        productService.registerOrderProduct(order, orderProductSpecifics);

        verify(productRepository, times(10)).findById(anyInt());
        verify(orderProductBaseDecorator, times(10)).save(any());
        verify(orderProductBaseDecorator, times(10)).cleanUpStock(any());
    }
}
