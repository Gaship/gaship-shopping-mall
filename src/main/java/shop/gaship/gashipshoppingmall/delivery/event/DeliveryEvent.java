package shop.gaship.gashipshoppingmall.delivery.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 배송 요청 이벤트를 발생시키기 위한 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class DeliveryEvent {
    private List<Integer> orderProductNos; 
}
