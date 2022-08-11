package shop.gaship.gashipshoppingmall.orderproduct.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;

/**
 * 주문 상품상세의 요구사항의 명세를 구현을 정의하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public interface OrderProductService {
    /**
     * 주문 상품 상세를 저장합니다.
     *
     * @param order                 어느 주문에 저장할 것인지에 대한 주문 엔티티 객체입니다.
     * @param orderProductSpecifics 상품의 고유번호, 쿠폰 고유번호, 수리설치 희망일자들을 담은 객체의 리스트 객체입니다.
     */
    void registerOrderProduct(Order order, List<OrderProductSpecificDto> orderProductSpecifics);

    /**
     * 주문 상품의 주문 상태를 변경합니다.
     *
     * @param orderProduct 상태를 바꿀 주문 상품입니다.
     * @param statusCode 바꿀 상태입니다.
     */
    void updateOrderStatus(OrderProduct orderProduct, StatusCode statusCode);

    /**
     * 주문 상품의 고유번호를 통해서 주문 상품의 상태 적용 및 취소금액을 적용합니다.
     *
     * @param orderProduct 주문 상품의 고유번호입니다.
     * @param statusCode 변경할 상태입니다.
     * @param cancellationAmount 취소금액입니다.
     */
    void updateOrderStatusByOrderProductNo(Integer orderProduct, OrderStatus statusCode,
                                           Long cancellationAmount);
}
