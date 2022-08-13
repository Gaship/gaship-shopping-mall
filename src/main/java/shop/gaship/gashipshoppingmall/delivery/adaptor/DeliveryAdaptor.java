package shop.gaship.gashipshoppingmall.delivery.adaptor;

import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;

/**
 * 배송 API 서버에 요청을 하기 위한 어댑터 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */

public interface DeliveryAdaptor {

    /**
     * 배송 서버에 배송 요청 (운송장 번호) 요청을 하는 메서드 입니다.
     *
     * @param deliveryDto 배송 요청 (운송장 번호 요청) 시에 필요한 정보가 저장된 dto 객체 입니다.
     */
    void createTrackingNo(DeliveryDto deliveryDto);
}
