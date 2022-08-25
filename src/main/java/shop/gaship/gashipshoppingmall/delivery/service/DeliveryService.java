package shop.gaship.gashipshoppingmall.delivery.service;

import shop.gaship.gashipshoppingmall.delivery.dto.response.DeliveryInfoStatusResponseDto;
import shop.gaship.gashipshoppingmall.delivery.dto.response.TrackingNoResponseDto;

/**
 * 배송과 관련된 비즈니스 로직을 처리하기 위한 서비스 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
public interface DeliveryService {

    /**
     * 운송장 번호를 요청할때 필요한 비즈니스 로직이 포함된 메서드 입니다.
     *
     * @param orderProductNo 운송장 번호를 요청 할때 필요한 해당 주문 상품 번호 입니다.
     */
    void createTrackingNo(Integer orderProductNo);

    /**
     * 배송 서버에서 보내는 운송장 번호를 받아서 관련 작업을 처리하는 메서드 입니다.
     *
     * @param trackingNoResponseDto 배송 서버에서 운송장번호를 전달할때 보내는 정보가 담긴 dto 객체 입니다.
     */
    void addTrackingNo(TrackingNoResponseDto trackingNoResponseDto);

    void changeDeliveryStatus(DeliveryInfoStatusResponseDto deliveryInfoStatusResponseDto);
}
