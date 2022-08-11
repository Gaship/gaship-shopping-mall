package shop.gaship.gashipshoppingmall.delivery.service;

import shop.gaship.gashipshoppingmall.delivery.dto.response.TrackingNoResponseDto;

/**
 * @author : 조재철
 * @since 1.0
 */
public interface DeliveryService {

    void createTrackingNo(Integer orderProductNo);

    void addTrackingNo(TrackingNoResponseDto trackingNoResponseDto);
}
