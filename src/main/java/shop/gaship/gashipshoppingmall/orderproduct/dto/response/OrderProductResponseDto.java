package shop.gaship.gashipshoppingmall.orderproduct.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문한상품의 정보를 볼수있게하는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@AllArgsConstructor
public class OrderProductResponseDto {
    private Integer orderNo;
    private Long totalOrderAmount;
    private LocalDateTime orderDatetime;
    private String receiptName;
    private String receiptPhoneNumber;
}
