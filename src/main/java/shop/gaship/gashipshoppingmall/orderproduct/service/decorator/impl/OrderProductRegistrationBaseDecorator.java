package shop.gaship.gashipshoppingmall.orderproduct.service.decorator.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.orderproduct.service.decorator.OrderProductRegistrationDecorator;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.NoMoreProductException;

/**
 * 주문 상세품목을 저장하며, 저장 후에 재고를 삭감할 기능을 가진 기본 데코레이터 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class OrderProductRegistrationBaseDecorator implements OrderProductRegistrationDecorator {
    private final OrderProductRepository orderProductRepository;

    /**
     * {@inheritDoc}
     *
     * @param orderProduct 저장할 주문 상세품목입니다.
     * @return {@inheritDoc}
     */
    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    /**
     * {@inheritDoc}
     *
     * @param product 삭감 할 품목 객체입니다.
     */
    @Override
    public void cleanUpStock(Product product) {
        int remainQuantity = product.getStockQuantity() - 1;

        if (remainQuantity < 0) {
            throw new NoMoreProductException();
        }

        product.updateStockQuantity(remainQuantity);
    }
}
