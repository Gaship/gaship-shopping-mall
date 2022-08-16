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
    /**
     * 회원이 주문한 주문에서 상세목록을 볼수있는 메서드입니다..
     *
     * @param memberNo 회원의 번호.
     * @param orderNo  상세조회할 주문번호.
     * @param pageable 관련 페이지객체.
     * @return the page 페이징 처리된 상세목록이 출력됩니다.
     */
    Page<OrderDetailResponseDto> findOrderDetails(Integer memberNo,
                                                  Integer orderNo,
                                                  Pageable pageable);

    /**
     * 회원의 모든 주문목록을 불러오기위한 메서드입니다.
     *
     * @param memberNo 회원의 번호.
     * @param pageable 관련 페이지객체.
     * @return the page 페이징 처리된 전체 주문목록이 출력됩니다.
     */
    Page<OrderListResponseDto> findAllOrders(Integer memberNo, Pageable pageable);


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
}
