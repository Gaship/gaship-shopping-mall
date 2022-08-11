package shop.gaship.gashipshoppingmall.delivery.adaptor;

import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;

/**
 * 배송 API 서버에 요청을 하기 위한 어댑터 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
public interface DeliveryAdaptor {

    void createTrackingNo(DeliveryDto deliveryDto);
}
