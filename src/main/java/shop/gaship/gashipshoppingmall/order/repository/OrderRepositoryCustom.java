package shop.gaship.gashipshoppingmall.order.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.order.dto.response.CancelOrderResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;

/**
 * querydsl 을 쓰기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface OrderRepositoryCustom {

    /**
     * Find cancel orders page.
     *
     * @param memberNo   회원의 번호
     * @param statusName 주문한 상세상태가 기입됩니다(예 : 취소)
     * @param pageable   관련 페지징객체
     * @return the page 페이징 처리된 취소주문내역들이 출력됩니다.
     */
    Page<OrderCancelResponseDto> findCancelOrders(Integer memberNo,
                                                  String statusName,
                                                  Pageable pageable);

    Optional<CancelOrderResponseDto> findOrderForCancel(Integer orderNo);
}
