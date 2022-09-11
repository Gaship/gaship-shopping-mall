package shop.gaship.gashipshoppingmall.order.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 * 결제 서버에서 결제 승인을 위해 주문의 정보를 조회하기 위한 요청에 대한
 * 응답 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class OrderPaymentResponseDto {
    private Integer no;
    private Long totalOrderAmount;
    private List<Integer> orderProductNos;
}
