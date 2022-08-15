package shop.gaship.gashipshoppingmall.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.delivery.adaptor.DeliveryAdaptor;
import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;
import shop.gaship.gashipshoppingmall.delivery.dto.response.TrackingNoResponseDto;
import shop.gaship.gashipshoppingmall.delivery.service.DeliveryService;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;

/**
 * 배송과 관련된 비즈니스 로직을 처리하기 위한 서비스 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderProductRepository orderProductRepository;
    private final DeliveryAdaptor deliveryAdaptor;
    private final Aes aes;

    /**
     * 운송장 번호를 요청할때 필요한 비즈니스 로직이 포함된 메서드 입니다.
     *
     * @param orderProductNo 운송장 번호를 요청 할때 필요한 해당 주문 상품 번호 입니다.
     */
    @Override
    public void createTrackingNo(Integer orderProductNo) {

        DeliveryDto deliveryDto =
            orderProductRepository.findOrderInfo(orderProductNo)
                                  .orElseThrow(OrderProductNotFoundException::new);


        deliveryDto.decodingReceiverAddress(aes.aesEcbDecode(deliveryDto.getReceiverAddress()));
        deliveryDto.decodingReceiverName(aes.aesEcbDecode(deliveryDto.getReceiverName()));
        deliveryDto.decodingReceiverPhone(aes.aesEcbDecode(deliveryDto.getReceiverPhone()));


        deliveryAdaptor.createTrackingNo(deliveryDto);
    }

    /**
     * 배송 서버에서 보내는 운송장 번호를 받아서 관련 작업을 처리하는 메서드 입니다.
     *
     * @param trackingNoResponseDto 배송 서버에서 운송장번호를 전달할때 보내는 정보가 담긴 dto 객체 입니다.
     */
    @Override
    public void addTrackingNo(TrackingNoResponseDto trackingNoResponseDto) {
        OrderProduct orderProduct =
            orderProductRepository.findById(Integer.parseInt(trackingNoResponseDto.getOrderProductNo()))
                                  .orElseThrow(OrderProductNotFoundException::new);

        orderProduct.addTrackingNo(trackingNoResponseDto.getTrackingNo());
    }
}