package shop.gaship.gashipshoppingmall.order.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문취소시 사용되는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCancelResponseDto {
    private Integer orderNo;
    private String address;
    private LocalDateTime orderDatetime;
    private String receiptName;
    private String receiptPhoneNumber;
    private String deliveryRequest;
    private Long totalOrderAmount;
    private Integer orderProductNo;
    private Long cancellationAmount;
    private String cancellationReason;
    private LocalDateTime cancellationDatetime;
}
