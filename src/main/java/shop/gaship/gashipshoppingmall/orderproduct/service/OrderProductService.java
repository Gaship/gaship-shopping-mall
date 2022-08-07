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

    void updateOrderStatus(OrderProduct orderProduct, StatusCode statusCode);

    void updateOrderStatusByOrderProductNo(Integer orderProduct, OrderStatus statusCode,
                                           Long cancellationAmount);
}
