package shop.gaship.gashipshoppingmall.orderproduct.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문 취소 요청을 받기 위한 DTO 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class OrderProductStatusCancelDto {
    private Integer paymentCancelHistoryNo;
    private List<CancelOrderInfo> cancelOrderInfos;
    private String cancelReason;

    /**
     * 주문 제품 취소 요청 상세정보입니다.
     *
     * @author 김민수
     * @since 1.0
     */
    @Getter
    @AllArgsConstructor
    public static class CancelOrderInfo {
        private Integer cancelOrderProductNo;
        private Long cancelAmount;
    }
}
