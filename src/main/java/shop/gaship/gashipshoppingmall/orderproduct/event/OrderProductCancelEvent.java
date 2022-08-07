package shop.gaship.gashipshoppingmall.orderproduct.event;

import java.util.List;
import lombok.AllArgsConstructor;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;

/**
 * 주문 상품의 등록 중 실패시 해당 이벤트를 발생시킵니다. 
 *
 * @author 김민수
 * @since 1.0
 */
@AllArgsConstructor
public class OrderProductCancelEvent {
    List<OrderProduct> orderProducts;
}
