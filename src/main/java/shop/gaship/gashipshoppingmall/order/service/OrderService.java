package shop.gaship.gashipshoppingmall.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.response.CancelOrderResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderPaymentResponseDto;
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
     * @return 주문 고유 번호입니다.
     */
    Integer insertOrder(OrderRegisterRequestDto orderRequest);

    /**
     * 주문 실행 후 결제시 필요한 정보들을 반환하기 위한 메서드입니다.
     *
     * @param orderNo 주문 번호입니다.
     * @return 주문 등록이 완료되고 결제를 위한 정보가 담긴 객체입니다.
     */
    OrderResponseDto findOrderForPayments(Integer orderNo);

    /**
     * 주문 결제가 완료되고 주문 상품들의 결제 번호를 주입시키기 위한 메서드입니다.
     *
     * @param orderNo    주문 번호입니다.
     * @param paymentKey 결제 번호입니다.
     */
    void orderPaymentsSuccess(Integer orderNo, String paymentKey);

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

    CancelOrderResponseDto findOrderForCancelPayment(Integer orderNo);

    OrderPaymentResponseDto findOrderDetailsForPayment(Integer orderNo);
}
