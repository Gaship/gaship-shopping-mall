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
 * @author : 조재철
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderProductRepository orderProductRepository;
    private final DeliveryAdaptor deliveryAdaptor;
    private final Aes aes;

    @Override
    public void createTrackingNo(Integer orderProductNo) {

        DeliveryDto deliveryDto =
            orderProductRepository.findOrderInfo(orderProductNo)
                                  .orElseThrow(OrderProductNotFoundException::new);

        // 암호화 되어 있는 정보를 디코딩 해서 보내줘야 함!!
//        deliveryDto.decodingReceiverAddress(aes.aesEcbDecode(deliveryDto.getReceiverAddress()));
//        deliveryDto.decodingReceiverName(aes.aesEcbDecode(deliveryDto.getReceiverName()));
//        deliveryDto.decodingReceiverPhone(aes.aesEcbDecode(deliveryDto.getReceiverPhone()));


        deliveryAdaptor.createTrackingNo(deliveryDto);
    }

    @Override
    public void addTrackingNo(TrackingNoResponseDto trackingNoResponseDto) {
        OrderProduct orderProduct =
            orderProductRepository.findById(Integer.parseInt(trackingNoResponseDto.getOrderProductNo()))
                                  .orElseThrow(OrderProductNotFoundException::new);

        orderProduct.addTrackingNo(trackingNoResponseDto.getTrackingNo());
    }
}