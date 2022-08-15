package shop.gaship.gashipshoppingmall.orderproduct.dto;

import java.util.List;
import lombok.Getter;

/**
 * 주문 취소 요청이 실패했을 경우 주문 재복구 요청을 받기위한 DTO 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
public class OrderProductCancellationFailDto {
    private Integer paymentCancelHistoryNo;
    private List<Integer> restoreOrderProductNos;
}
