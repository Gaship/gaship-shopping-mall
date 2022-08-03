package shop.gaship.gashipshoppingmall.orderproduct.service.decorator;

import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.product.entity.Product;

/**
 * 주문 상세품목을 저장하며, 저장 후에 재고를 삭감할 기능을 기본적으로 가질 형태를 가진 데코레이터 인터페이스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public interface OrderProductRegistrationDecorator {

    /**
     * 주문 상세품목을 저장합니다.
     *
     * @param orderProduct 저장할 주문 상세품목입니다.
     * @return 저장된 후의 주문 상세품목 결과를 반환합니다.
     */
    OrderProduct save(OrderProduct orderProduct);

    /**
     * 재고를 삭감(정리)합니다.
     *
     * @param product 삭감 할 품목 객체입니다.
     */
    void cleanUpStock(Product product);
}
