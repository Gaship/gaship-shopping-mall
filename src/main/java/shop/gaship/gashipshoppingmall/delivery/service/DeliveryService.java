package shop.gaship.gashipshoppingmall.delivery.service;

import shop.gaship.gashipshoppingmall.delivery.dto.response.TrackingNoResponseDto;

/**
 * 배송과 관련된 비즈니스 로직을 처리하기 위한 서비스 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
public interface DeliveryService {

    void createTrackingNo(Integer orderProductNo);

    void addTrackingNo(TrackingNoResponseDto trackingNoResponseDto);
}
