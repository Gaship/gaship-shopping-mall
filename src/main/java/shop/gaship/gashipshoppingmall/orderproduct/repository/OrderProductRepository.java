package shop.gaship.gashipshoppingmall.orderproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;

/**
 * 주문상품 관련 repository.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
}