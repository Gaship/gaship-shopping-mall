package shop.gaship.gashipshoppingmall.delivery.adaptor;

import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;

/**
 * @author : 조재철
 * @since 1.0
 */
public interface DeliveryAdaptor {

    void createTrackingNo(DeliveryDto deliveryDto);
}
