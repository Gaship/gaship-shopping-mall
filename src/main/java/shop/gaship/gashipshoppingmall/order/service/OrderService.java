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

    /**
     * 회원이 주문한 주문에서 상세목록을 볼수있는 메서드입니다..
     *
     * @param memberNo 회원의 번호.
     * @param orderNo  상세조회할 주문번호.
     * @param pageable 관련 페이지객체.
     * @return the page 페이징 처리된 상세목록이 출력됩니다.
     */
    Page<OrderDetailResponseDto> findMemberOrderDetails(Integer memberNo,
                                                        Integer orderNo, Pageable pageable);

    /**
     * 회원의 모든 주문목록을 불러오기위한 메서드입니다.
     *
     * @param memberNo 회원의 번호.
     * @param pageable 관련 페이지객체.
     * @return the page 페이징 처리된 전체 주문목록이 출력됩니다.
     */
    Page<OrderListResponseDto> findAllMemberOrders(Integer memberNo, Pageable pageable);

    /**
     * Find cancel orders page.
     *
     * @param memberNo   회원의 번호
     * @param statusName 주문한 상세상태가 기입됩니다(예 : 취소)
     * @param pageable   관련 페지징객체
     * @return the page 페이징 처리된 취소주문내역들이 출력됩니다.
     */
    Page<OrderCancelResponseDto> findMemberCancelOrders(Integer memberNo,
                                                        String statusName,
                                                        Pageable pageable);

}
