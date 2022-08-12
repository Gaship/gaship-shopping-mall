package shop.gaship.gashipshoppingmall.delivery.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.delivery.adaptor.DeliveryAdaptor;
import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;
import shop.gaship.gashipshoppingmall.delivery.dto.response.TrackingNoResponseDto;
import shop.gaship.gashipshoppingmall.delivery.service.DeliveryService;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;

/**
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

    @Autowired
    DeliveryService deliveryService;

    @Test
    void createTrackingNo() {

        // given
        DeliveryDto deliveryDto = new DeliveryDto("유민철", "창원", "0101231234", 1);

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
}