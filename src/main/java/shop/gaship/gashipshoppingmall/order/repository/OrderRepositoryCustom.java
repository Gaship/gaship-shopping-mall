package shop.gaship.gashipshoppingmall.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderDetailResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderListResponseDto;

/**
 * querydsl 을 쓰기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface OrderRepositoryCustom {
    Page<OrderDetailResponseDto> findOrderDetails(Integer memberNo,
                                                  Integer orderNo,
                                                  Pageable pageable);

    Page<OrderListResponseDto> findAllOrders(Integer memberNo, Pageable pageable);


    Page<OrderCancelResponseDto> findCancelOrders(Integer memberNo, String statusName, Pageable pageable);
}
