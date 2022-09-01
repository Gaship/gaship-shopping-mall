package shop.gaship.gashipshoppingmall.delivery.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.delivery.adaptor.DeliveryAdaptor;
import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;
import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryStatus;
import shop.gaship.gashipshoppingmall.delivery.dto.response.DeliveryInfoStatusResponseDto;
import shop.gaship.gashipshoppingmall.delivery.dto.response.TrackingNoResponseDto;
import shop.gaship.gashipshoppingmall.delivery.service.DeliveryService;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;

/**
 * 배송 관련 service test 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */

@ExtendWith(SpringExtension.class)
@Import(DeliveryServiceImpl.class)
class DeliveryServiceImplTest {

    @MockBean
    OrderProductRepository orderProductRepository;

    @MockBean
    DeliveryAdaptor deliveryAdaptor;

    @MockBean
    Aes aes;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    @Autowired
    DeliveryService deliveryService;

    @Test
    void createTrackingNo() {

        // given
        DeliveryDto deliveryDto = new DeliveryDto("유민철", "창원", "집", "01012341234", 1);

        when(orderProductRepository.findOrderInfo(any())).thenReturn(Optional.of(deliveryDto));

        // when
        deliveryService.createTrackingNo(1);

        // then
        verify(orderProductRepository).findOrderInfo(1);
        verify(deliveryAdaptor).createTrackingNo(deliveryDto);
    }

    @Test
    void createTrackingNoOrderProductNotFoundExceptionTest() {

        // given
        when(orderProductRepository.findOrderInfo(1))
            .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> deliveryService.createTrackingNo(1)).isInstanceOf(
            OrderProductNotFoundException.class).hasMessageContaining("해당 주문상품을 찾을 수 없습니다.");
    }

    @Test
    void addTrackingNo() {

        // given
        TrackingNoResponseDto trackingNoResponseDto = new TrackingNoResponseDto("1", "1");
        OrderProduct orderProduct = OrderProduct.builder()
                                                .build();
        when(orderProductRepository.findById(Integer.parseInt(trackingNoResponseDto.getOrderProductNo()))).thenReturn(
            Optional.of(orderProduct));

        // when
        deliveryService.addTrackingNo(trackingNoResponseDto);

        //then
        verify(orderProductRepository).findById(1);
    }

    @Test
    void addTrackingNoOrderProductNotFoundException() {

        // given
        TrackingNoResponseDto trackingNoResponseDto = new TrackingNoResponseDto("1", "1");

        when(orderProductRepository.findById(any()))
            .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> deliveryService.addTrackingNo(trackingNoResponseDto)).isInstanceOf(
            OrderProductNotFoundException.class).hasMessageContaining("해당 주문상품을 찾을 수 없습니다.");
    }

    @Test
    void changeDeliveryStatusDelivering() {
        // given
        DeliveryInfoStatusResponseDto deliveryInfoStatusResponseDto = new DeliveryInfoStatusResponseDto();
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "orderProductNo", "1");
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "status", DeliveryStatus.DELIVERING.toString());
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "arrivalTime", LocalDateTime.now());

        StatusCode statusCode = StatusCode.builder()
                                          .statusCodeName(OrderStatus.SHIPPING.getValue())
                                          .priority(1)
                                          .groupCodeName(OrderStatus.GROUP)
                                          .build();

        OrderProduct orderProduct = OrderProduct.builder()
                                                .orderStatusCode(statusCode)
                                                .build();

        ReflectionTestUtils.setField(orderProduct, "no", 1);

        when(orderProductRepository.findById(orderProduct.getNo()))
            .thenReturn(Optional.of(orderProduct));

        when(statusCodeRepository.findByStatusCodeName(any())).thenReturn(Optional.of(statusCode));

        // when
        deliveryService.changeDeliveryStatus(deliveryInfoStatusResponseDto);

        // then
        verify(orderProductRepository).findById(orderProduct.getNo());

    }

    @Test
    void changeDeliveryStatusArrival() {
        // given
        DeliveryInfoStatusResponseDto deliveryInfoStatusResponseDto = new DeliveryInfoStatusResponseDto();
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "orderProductNo", "1");
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "status", DeliveryStatus.ARRIVAL.toString());
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "arrivalTime", LocalDateTime.now());

        StatusCode statusCode = StatusCode.builder()
                                          .statusCodeName(OrderStatus.DELIVERY_COMPLETE.getValue())
                                          .priority(1)
                                          .groupCodeName(OrderStatus.GROUP)
                                          .build();

        OrderProduct orderProduct = OrderProduct.builder()
                                                .orderStatusCode(statusCode)
                                                .build();

        ReflectionTestUtils.setField(orderProduct, "no", 1);

        when(orderProductRepository.findById(orderProduct.getNo()))
            .thenReturn(Optional.of(orderProduct));

        when(statusCodeRepository.findByStatusCodeName(any())).thenReturn(Optional.of(statusCode));

        // when
        deliveryService.changeDeliveryStatus(deliveryInfoStatusResponseDto);

        // then
        verify(orderProductRepository).findById(orderProduct.getNo());

    }

    @Test
    void changeDeliveryStatusNotFoundOrderProductTest() {
        // given
        DeliveryInfoStatusResponseDto deliveryInfoStatusResponseDto = new DeliveryInfoStatusResponseDto();
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "orderProductNo", "123");
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "status", "배송 중");
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "arrivalTime", LocalDateTime.now());

        when(orderProductRepository.findById(any()))
            .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> deliveryService.changeDeliveryStatus(deliveryInfoStatusResponseDto)).isInstanceOf(
            OrderProductNotFoundException.class).hasMessageContaining("해당 주문상품을 찾을 수 없습니다.");

    }

}