package shop.gaship.gashipshoppingmall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.order.entity.Order;

/**
 * 주문관련 repository 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
