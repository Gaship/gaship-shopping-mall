package shop.gaship.gashipshoppingmall.orderproduct.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductCancellationFailDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductStatusCancelDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductStatusChangeDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductResponseDto;

/**
 * 주문 상품상세의 요구사항의 명세를 구현을 정의하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public interface OrderProductService {
    /**
     * 주문 상품 상세를 저장합니다.
     *
     * @param order                 어느 주문에 저장할 것인지에 대한 주문 엔티티 객체입니다.
     * @param orderProductSpecifics 상품의 고유번호, 쿠폰 고유번호, 수리설치 희망일자들을 담은 객체의 리스트 객체입니다.
     */
    void registerOrderProduct(Order order, List<OrderProductSpecificDto> orderProductSpecifics);

    Page<OrderProductResponseDto> findMemberOrders(Integer memberNo,
                                                   Pageable pageable);

    Page<OrderProductDetailResponseDto> findMemberOrderProductDetail(Integer orderProductNo, Integer memberNo, Pageable pageable);

    /**
     * 주문 상품의 주문 상태를 교환으로 변경합니다.
     *
     * @param orderProductStatusChangeDto 주문 상태를 교환상태로 바꿀 주문 상품입니다.
     */
    void updateOrderProductStatusToChange(OrderProductStatusChangeDto orderProductStatusChangeDto);

    /**
     * 주문 상품의 고유번호를 통해서 주문 상품의 상태 적용 및 취소금액을 적용합니다.
     *
     * @param orderProductStatusCancelDto 주문 상품의 취소정보입니다.
     */
    void updateOrderProductStatusToCancel(OrderProductStatusCancelDto orderProductStatusCancelDto);

    /**
     * 주문 취소를 실행실패했을 경우 주문을 복구를 실행합니다.
     *
     * @param orderProductCancellationFailDto 주문 취소 상품들의 정보입니다.
     */
    void restoreOrderProduct(OrderProductCancellationFailDto orderProductCancellationFailDto);
}
