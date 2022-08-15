package shop.gaship.gashipshoppingmall.orderproduct.dto;

import java.util.List;
import lombok.Getter;

/**
 * 주문 교환요청을 받는 DTO 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
public class OrderProductStatusChangeDto {
    private List<Integer> orderProductNos;
}
