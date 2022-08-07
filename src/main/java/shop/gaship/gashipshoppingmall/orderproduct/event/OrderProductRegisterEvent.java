package shop.gaship.gashipshoppingmall.orderproduct.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;

/**
 * 주문의 등록이 정상적으로 완료 하였을 시 주문 상품을 등록 발생시키는 이벤트입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class OrderProductRegisterEvent {
    private Order order;
    private List<OrderProductSpecificDto> orderProductSpecifics;
}
