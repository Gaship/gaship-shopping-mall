package shop.gaship.gashipshoppingmall.orderproduct.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

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
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.event.CouponUseEvent;
import shop.gaship.gashipshoppingmall.orderproduct.event.CouponUseEventHandler;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductCancelEvent;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductCancelEventHandler;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.orderproduct.service.impl.OrderProductServiceImpl;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
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

    @MockBean
    private CouponUseEventHandler couponUseEventHandler;


    @Test
    @DisplayName("주문 상품 등록")
    void registerOrderProduct() {
        Order order = OrderDummy.createOrderDummy();
        List<OrderProductSpecificDto> orderProductSpecifics = new ArrayList<>();
        OrderProductSpecificDto orderProductSpecific = new OrderProductSpecificDto();
        ReflectionTestUtils.setField(orderProductSpecific, "productNo", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "amount", 10000000L);
        ReflectionTestUtils.setField(orderProductSpecific, "additionalWarrantyPeriod", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "couponNo", null);
        ReflectionTestUtils.setField(orderProductSpecific, "couponAmount", 0L);
        ReflectionTestUtils.setField(orderProductSpecific, "hopeDate", null);

        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));
        given(productRepository.findById(any()))
            .willReturn(Optional.of(ProductDummy.dummy()));

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
            .should(times(5))
            .findById(1);

        then(orderProductRepository)
            .should(times(1))
            .saveAll(anyList());

        then(couponUseEventHandler)
            .should(never())
            .handle(any(CouponUseEvent.class));

        assertThat(order.getTotalOrderAmount()).isEqualTo(1000000L);
    }

    @Test
    @DisplayName("주문 상품 등록 쿠폰 사용 케이스")
    void registerOrderProductUseCoupon() {
        Order order = OrderDummy.createOrderDummy();
        List<OrderProductSpecificDto> orderProductSpecifics = new ArrayList<>();
        OrderProductSpecificDto orderProductSpecific = new OrderProductSpecificDto();
        ReflectionTestUtils.setField(orderProductSpecific, "productNo", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "amount", 10000000L);
        ReflectionTestUtils.setField(orderProductSpecific, "additionalWarrantyPeriod", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "couponNo", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "couponAmount", 1000L);
        ReflectionTestUtils.setField(orderProductSpecific, "hopeDate", null);

        IntStream.range(0,5).forEach(i -> orderProductSpecifics.add(orderProductSpecific));

        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));
        given(productRepository.findById(any()))
            .willReturn(Optional.of(ProductDummy.dummy()));

        given(orderProductRepository.saveAll(anyList()))
            .willReturn(IntStream.range(0, 5)
                .mapToObj(value -> OrderProductDummy.dummy())
                .collect(Collectors.toUnmodifiableList()));

        doNothing().when(couponUseEventHandler).handle(any(CouponUseEvent.class));

        orderProductService.registerOrderProduct(order, orderProductSpecifics);

        then(statusCodeRepository)
            .should(times(1))
            .findByStatusCodeName(anyString());

        then(productRepository)
            .should(times(5))
            .findById(1);

        then(orderProductRepository)
            .should(times(1))
            .saveAll(anyList());

        then(couponUseEventHandler)
            .should(times(1))
            .handle(any(CouponUseEvent.class));

        assertThat(order.getTotalOrderAmount()).isEqualTo(1000000L);
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
