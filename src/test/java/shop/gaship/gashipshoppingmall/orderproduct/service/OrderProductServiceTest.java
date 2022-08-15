package shop.gaship.gashipshoppingmall.orderproduct.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductCancellationFailDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductStatusCancelDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductStatusChangeDto;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.event.CouponUseCancelEvent;
import shop.gaship.gashipshoppingmall.orderproduct.event.CouponUseCancelEventHandler;
import shop.gaship.gashipshoppingmall.orderproduct.event.CouponUseEvent;
import shop.gaship.gashipshoppingmall.orderproduct.event.CouponUseEventHandler;
import shop.gaship.gashipshoppingmall.orderproduct.exception.InvalidOrderCancellationHistoryNo;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.orderproduct.service.impl.OrderProductServiceImpl;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.InvalidOrderStatusException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;

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

    @MockBean
    private CouponUseCancelEventHandler couponUseCancelEventHandler;


    @Test
    @DisplayName("주문 상품 등록")
    void registerOrderProduct() {
        Order order = OrderDummy.createOrderDummy();
        List<OrderProductSpecificDto> orderProductSpecifics = new ArrayList<>();
        OrderProductSpecificDto orderProductSpecific = new OrderProductSpecificDto();
        ReflectionTestUtils.setField(orderProductSpecific, "productNo", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "amount", 10000000L);
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

        IntStream.range(0, 5).forEach(i -> orderProductSpecifics.add(orderProductSpecific));
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
        ReflectionTestUtils.setField(orderProductSpecific, "couponNo", 1);
        ReflectionTestUtils.setField(orderProductSpecific, "couponAmount", 1000L);
        ReflectionTestUtils.setField(orderProductSpecific, "hopeDate", null);

        IntStream.range(0, 5).forEach(i -> orderProductSpecifics.add(orderProductSpecific));

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
    @DisplayName("주문 취소 혹은 반품 테스트")
    void orderProductCancellationTest() {
        OrderProductStatusCancelDto orderProductStatusCancelDto = new OrderProductStatusCancelDto(
            1,
            IntStream.range(0, 5).mapToObj(value ->
                    new OrderProductStatusCancelDto.CancelOrderInfo(
                        value,
                        10000L,
                        "배송준비중"))
                .collect(Collectors.toUnmodifiableList()));

        given(orderProductRepository.findById(0))
            .willReturn(Optional.of(OrderProductDummy.dummy()));
        given(orderProductRepository.findById(1))
            .willReturn(Optional.of(OrderProductDummy.dummy()));
        given(orderProductRepository.findById(2))
            .willReturn(Optional.of(OrderProductDummy.dummy()));
        given(orderProductRepository.findById(3))
            .willReturn(Optional.of(OrderProductDummy.dummy()));
        given(orderProductRepository.findById(4))
            .willReturn(Optional.of(OrderProductDummy.dummy()));

        given(statusCodeRepository.findByStatusCodeName(OrderStatus.CANCEL_COMPLETE.getValue()))
            .willReturn(Optional.of(StatusCode.builder()
                    .statusCodeName(OrderStatus.CANCEL_COMPLETE.getValue())
                    .priority(1)
                    .groupCodeName("")
                    .explanation("")
                .build()));

        given(orderProductRepository.findAllById(anyList()))
            .willReturn(IntStream.range(0,5)
                .mapToObj(value -> OrderProductDummy.dummy())
                .collect(Collectors.toUnmodifiableList()));

        doNothing().when(couponUseCancelEventHandler).handle(any(CouponUseCancelEvent.class));

        orderProductService.updateOrderProductStatusToCancel(orderProductStatusCancelDto);

        then(orderProductRepository)
            .should(times(5))
            .findById(anyInt());

        then(statusCodeRepository)
            .should(times(1))
            .findByStatusCodeName(any());

        then(couponUseCancelEventHandler)
            .should(times(1))
            .handle(any(CouponUseCancelEvent.class));
    }

    @Test
    @DisplayName("주문 취소, 반품 실패 테스트 - 취소나 반품할 상태가 아닌경우 (배송준비중, 배송중, 배송완료) ")
    void orderProductCancellationFailureTest() {
        OrderProductStatusCancelDto orderProductStatusCancelDto = new OrderProductStatusCancelDto(
            1,
            IntStream.range(0, 5).mapToObj(value ->
                    new OrderProductStatusCancelDto.CancelOrderInfo(
                        value,
                        10000L,
                        "배송준비중"))
                .collect(Collectors.toUnmodifiableList()));

        OrderProduct dummy = OrderProductDummy.dummy();
        StatusCode testCancelStatus = StatusCode.builder()
            .statusCodeName("취소완료")
            .groupCodeName("주문")
            .explanation("")
            .priority(1)
            .build();
        ReflectionTestUtils.setField(dummy, "orderStatusCode", testCancelStatus);

        given(orderProductRepository.findById(0))
            .willReturn(Optional.of(dummy));

        given(statusCodeRepository.findByStatusCodeName(OrderStatus.CANCEL_COMPLETE.getValue()))
            .willReturn(Optional.of(StatusCode.builder()
                .statusCodeName(OrderStatus.CANCEL_COMPLETE.getValue())
                .priority(1)
                .groupCodeName("")
                .explanation("")
                .build()));

        assertThatThrownBy(() ->
            orderProductService.updateOrderProductStatusToCancel(orderProductStatusCancelDto))
            .isInstanceOf(InvalidOrderStatusException.class)
            .hasMessage("해당 주문 상태로는 취소상태로 변경이 불가능합니다.");
    }

    @Test
    @DisplayName("주문 교환 테스트")
    void orderProductChangeTest() {
        OrderProductStatusChangeDto orderProductStatusChangeDto = new OrderProductStatusChangeDto();
        ReflectionTestUtils.setField(orderProductStatusChangeDto, "orderProductNos", List.of(1,2,3,4,5));

        List<OrderProduct> dummies = IntStream.range(1,6).mapToObj(i -> {
            OrderProduct dummy = OrderProductDummy.dummy();
            if (i % 2 == 0){
                StatusCode orderStatus = StatusCode.builder()
                    .statusCodeName(OrderStatus.SHIPPING.getValue())
                    .priority(1)
                    .groupCodeName("")
                    .explanation("")
                    .build();
                ReflectionTestUtils.setField(dummy, "orderStatusCode", orderStatus);
            } else {
                StatusCode orderStatus = StatusCode.builder()
                    .statusCodeName(OrderStatus.DELIVERY_COMPLETE.getValue())
                    .priority(1)
                    .groupCodeName("")
                    .explanation("")
                    .build();
                ReflectionTestUtils.setField(dummy, "orderStatusCode", orderStatus);
            }
            return dummy;
        }).collect(Collectors.toUnmodifiableList());

        given(orderProductRepository.findById(1))
            .willReturn(Optional.of(dummies.get(0)));
        given(orderProductRepository.findById(2))
            .willReturn(Optional.of(dummies.get(1)));
        given(orderProductRepository.findById(3))
            .willReturn(Optional.of(dummies.get(2)));
        given(orderProductRepository.findById(4))
            .willReturn(Optional.of(dummies.get(3)));
        given(orderProductRepository.findById(5))
            .willReturn(Optional.of(dummies.get(4)));

        given(statusCodeRepository.findByStatusCodeName(OrderStatus.EXCHANGE_RECEPTION.getValue()))
            .willReturn(Optional.of(StatusCode.builder()
                .statusCodeName(OrderStatus.EXCHANGE_RECEPTION.getValue())
                .priority(1)
                .groupCodeName("")
                .explanation("")
                .build()));

        orderProductService.updateOrderProductStatusToChange(orderProductStatusChangeDto);

        then(orderProductRepository)
            .should(times(5))
            .findById(anyInt());

        then(statusCodeRepository)
            .should(times(1))
            .findByStatusCodeName(OrderStatus.EXCHANGE_RECEPTION.getValue());
    }

    @Test
    @DisplayName("주문 교환 테스트")
    void orderProductChangeFailureTest() {
        OrderProductStatusChangeDto orderProductStatusChangeDto = new OrderProductStatusChangeDto();
        ReflectionTestUtils.setField(orderProductStatusChangeDto, "orderProductNos", List.of(1,2,3,4,5));

        List<OrderProduct> dummies = IntStream.range(1,6).mapToObj(i -> {
            OrderProduct dummy = OrderProductDummy.dummy();
            if (i % 2 == 0){
                StatusCode orderStatus = StatusCode.builder()
                    .statusCodeName(OrderStatus.SHIPPING.getValue())
                    .priority(1)
                    .groupCodeName("")
                    .explanation("")
                    .build();
                ReflectionTestUtils.setField(dummy, "orderStatusCode", orderStatus);
            } else {
                StatusCode orderStatus = StatusCode.builder()
                    .statusCodeName(OrderStatus.DELIVERY_COMPLETE.getValue())
                    .priority(1)
                    .groupCodeName("")
                    .explanation("")
                    .build();
                ReflectionTestUtils.setField(dummy, "orderStatusCode", orderStatus);
            }
            return dummy;
        }).collect(Collectors.toUnmodifiableList());

        given(orderProductRepository.findById(1))
            .willReturn(Optional.of(dummies.get(0)));
        given(orderProductRepository.findById(2))
            .willReturn(Optional.of(dummies.get(1)));
        given(orderProductRepository.findById(3))
            .willReturn(Optional.of(dummies.get(2)));
        given(orderProductRepository.findById(4))
            .willReturn(Optional.of(dummies.get(3)));
        given(orderProductRepository.findById(5))
            .willReturn(Optional.of(dummies.get(4)));

        given(statusCodeRepository.findByStatusCodeName(OrderStatus.EXCHANGE_RECEPTION.getValue()))
            .willReturn(Optional.of(StatusCode.builder()
                .statusCodeName(OrderStatus.EXCHANGE_RECEPTION.getValue())
                .priority(1)
                .groupCodeName("")
                .explanation("")
                .build()));

        orderProductService.updateOrderProductStatusToChange(orderProductStatusChangeDto);

        then(orderProductRepository)
            .should(times(5))
            .findById(anyInt());

        then(statusCodeRepository)
            .should(times(1))
            .findByStatusCodeName(OrderStatus.EXCHANGE_RECEPTION.getValue());
    }

    @Test
    @DisplayName("주문 취소(결제 서버에서 실패시) 복구 테스트")
    void orderProductRestoreTest() {
        OrderProductCancellationFailDto orderProductCancellationFailDto = new OrderProductCancellationFailDto();
        ReflectionTestUtils.setField(orderProductCancellationFailDto, "paymentCancelHistoryNo", 1);
        ReflectionTestUtils.setField(orderProductCancellationFailDto, "restoreOrderProductNos", List.of(1, 2, 3, 4, 5));

        given(orderProductRepository.findAllById(anyList()))
            .willReturn(IntStream.range(0, 5)
                .mapToObj(value -> {
                    OrderProduct dummy = OrderProductDummy.dummy();
                    ReflectionTestUtils.setField(dummy, "orderStatusCode", StatusCode.builder()
                            .statusCodeName(OrderStatus.CANCEL_COMPLETE.getValue())
                            .groupCodeName("")
                            .priority(1)
                            .explanation("")
                            .build());

                    ReflectionTestUtils.setField(dummy, "paymentCancelHistoryNo", 1);

                    return dummy;
                })
                .collect(Collectors.toUnmodifiableList()));

        given(statusCodeRepository.findByStatusCodeName(OrderStatus.DELIVERY_PREPARING.getValue()))
            .willReturn(Optional.of(StatusCode.builder()
                .statusCodeName(OrderStatus.DELIVERY_PREPARING.getValue())
                .priority(1)
                .groupCodeName("")
                .explanation("")
                .build()));

        willDoNothing().given(couponUseEventHandler).handle(any(CouponUseEvent.class));

        orderProductService.restoreOrderProduct(orderProductCancellationFailDto);

        then(orderProductRepository)
            .should(times(1))
            .findAllById(orderProductCancellationFailDto.getRestoreOrderProductNos());

        then(statusCodeRepository)
            .should(times(1))
            .findByStatusCodeName(OrderStatus.DELIVERY_PREPARING.getValue());

        then(couponUseEventHandler)
            .should(times(1))
            .handle(any(CouponUseEvent.class));
    }

    @Test
    @DisplayName("주문 취소(결제 서버에서 실패시) 복구 실패 테스트 - 취소 번호 불일치로인한 실패")
    void orderProductRestoreNotMatchCancelNoFailureTest() {
        OrderProductCancellationFailDto orderProductCancellationFailDto = new OrderProductCancellationFailDto();
        ReflectionTestUtils.setField(orderProductCancellationFailDto, "paymentCancelHistoryNo", 1);
        ReflectionTestUtils.setField(orderProductCancellationFailDto, "restoreOrderProductNos", List.of(1, 2, 3, 4, 5));

        given(orderProductRepository.findAllById(anyList()))
            .willReturn(IntStream.range(0, 5)
                .mapToObj(value -> {
                    OrderProduct dummy = OrderProductDummy.dummy();
                    ReflectionTestUtils.setField(dummy, "orderStatusCode", StatusCode.builder()
                        .statusCodeName(OrderStatus.CANCEL_COMPLETE.getValue())
                        .groupCodeName("")
                        .priority(1)
                        .explanation("")
                        .build());

                    return dummy;
                })
                .collect(Collectors.toUnmodifiableList()));

        given(statusCodeRepository.findByStatusCodeName(OrderStatus.DELIVERY_PREPARING.getValue()))
            .willReturn(Optional.of(StatusCode.builder()
                .statusCodeName(OrderStatus.DELIVERY_PREPARING.getValue())
                .priority(1)
                .groupCodeName("")
                .explanation("")
                .build()));

        willDoNothing().given(couponUseEventHandler).handle(any(CouponUseEvent.class));

        assertThatThrownBy(() ->
            orderProductService.restoreOrderProduct(orderProductCancellationFailDto))
            .isInstanceOf(InvalidOrderCancellationHistoryNo.class)
            .hasMessage("일치하지않는 취소이력번호입니다.");
    }

    @Test
    @DisplayName("주문 취소(결제 서버에서 실패시) 복구 실패 테스트 - 취소 상태가 불일치로인한 작업 실패")
    void orderProductRestoreInvalidStatusFailureTest() {
        OrderProductCancellationFailDto orderProductCancellationFailDto = new OrderProductCancellationFailDto();
        ReflectionTestUtils.setField(orderProductCancellationFailDto, "paymentCancelHistoryNo", 1);
        ReflectionTestUtils.setField(orderProductCancellationFailDto, "restoreOrderProductNos", List.of(1, 2, 3, 4, 5));

        given(orderProductRepository.findAllById(anyList()))
            .willReturn(IntStream.range(0, 5)
                .mapToObj(value -> OrderProductDummy.dummy())
                .collect(Collectors.toUnmodifiableList()));

        given(statusCodeRepository.findByStatusCodeName(OrderStatus.DELIVERY_PREPARING.getValue()))
            .willReturn(Optional.of(StatusCode.builder()
                .statusCodeName(OrderStatus.DELIVERY_PREPARING.getValue())
                .priority(1)
                .groupCodeName("")
                .explanation("")
                .build()));

        willDoNothing().given(couponUseEventHandler).handle(any(CouponUseEvent.class));

        assertThatThrownBy(() ->
            orderProductService.restoreOrderProduct(orderProductCancellationFailDto))
            .isInstanceOf(InvalidOrderStatusException.class)
            .hasMessage("해당 주문 상태로는 취소 복구 상태로 변경이 불가능합니다.");
    }
}
