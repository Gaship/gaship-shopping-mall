package shop.gaship.gashipshoppingmall.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderDetailResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderListResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderResponseDto;

/**
 * 주문을 위해 처리될 서비스를 정의하는 인터페이스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public interface OrderService {
    /**
     * 주문을 생성하는 메서드입니다.
     *
     * @param orderRequest 주문을 요청하기 위한 데이터가 담긴 객체입니다.
     */
    Integer insertOrder(OrderRegisterRequestDto orderRequest);

    OrderResponseDto findOrderForPayments(Integer orderNo);

    Page<OrderDetailResponseDto> findOrderDetails(Integer memberNo,
                                                  Integer orderNo, Pageable pageable);

    Page<OrderListResponseDto> findAllOrders(Integer memberNo, Pageable pageable);

    Page<OrderCancelResponseDto> findCancelOrders(Integer memberNo,
                                                  String statusName,
                                                  Pageable pageable);

}
