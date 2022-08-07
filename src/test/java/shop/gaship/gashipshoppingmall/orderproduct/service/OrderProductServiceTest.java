package shop.gaship.gashipshoppingmall.orderproduct.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
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
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.orderproduct.service.impl.OrderProductServiceImpl;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
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
    private OrderProductService orderProductService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private StatusCodeRepository statusCodeRepository;

    @MockBean
    private OrderProductRepository orderProductRepository;


    @Test
    @DisplayName("주문 상품 등록")
    void registerOrderProduct() {
        Order order = OrderDummy.createOrderDummy();
        List<OrderProductSpecificDto> orderProductSpecifics = new ArrayList<>();
        OrderProductSpecificDto orderProductSpecific = new OrderProductSpecificDto();
        ReflectionTestUtils.setField(orderProductSpecific, "productNo", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "additionalWarrantyPeriod", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "couponWarrantyPeriod", 0);
        ReflectionTestUtils.setField(orderProductSpecific, "couponNo", null);
        ReflectionTestUtils.setField(orderProductSpecific, "isUseCoupon", false);
        ReflectionTestUtils.setField(orderProductSpecific, "hopeDate", null);

        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));
        given(productRepository.findAllById(anyList()))
            .willReturn(IntStream.range(0, 5).mapToObj(value ->
                ProductDummy.dummy()).collect(Collectors.toUnmodifiableList()));

        given(orderProductRepository.saveAll(anyList()))
            .willReturn(IntStream.range(0, 5)
                .mapToObj(value -> OrderProductDummy.dummy())
                .collect(Collectors.toUnmodifiableList()));

        IntStream.range(0,5).forEach(i -> orderProductSpecifics.add(orderProductSpecific));
        orderProductService.registerOrderProduct(order, orderProductSpecifics);

        then(statusCodeRepository)
            .should(times(1))
            .findByStatusCodeName(anyString());

        then(productRepository)
            .should(times(1))
            .findAllById(List.of(1,1,1,1,1));

        then(orderProductRepository)
            .should(times(1))
            .saveAll(anyList());

        assertThat(order.getTotalOrderAmount()).isEqualTo(50000L);
    }

    @Test
    @DisplayName("주문 상품의 금액을 계산하는 테스트")
    void calculateTotalAmount() {
        long amount = 1000000L;
        int additionalWarrantyPeriod = 12;
        int couponWarrantyPeriod = 11;

        double result1 = amount + (0.1 * amount) * (additionalWarrantyPeriod - couponWarrantyPeriod);
        couponWarrantyPeriod = 0;
        double result2 = amount + (0.1 * amount) * (additionalWarrantyPeriod - couponWarrantyPeriod);
        double result3 = amount + (0.1 * amount) * 0;

        assertThat(Double.valueOf(result1).longValue())
            .isEqualTo(1000000L + 100000);
        assertThat(Double.valueOf(result2).longValue())
            .isEqualTo(1000000L + 1200000);
        assertThat(Double.valueOf(result3).longValue())
            .isEqualTo(1000000L);
    }
}
