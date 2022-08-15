package shop.gaship.gashipshoppingmall.order.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문리스트를 반환하기위한 dto 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListResponseDto {
    private Integer orderNo;
    private String address;
    private LocalDateTime orderDatetime;
    private String receiptName;
    private String receiptPhoneNumber;
    private String deliveryRequest;
    private Long totalOrderAmount;
}
