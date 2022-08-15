package shop.gaship.gashipshoppingmall.order.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문상세정보를 받기위한 dto 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponseDto {
    private String statusCodeName;
    private LocalDateTime orderDatetime;
    private String receiptName;
    private String receiptPhoneNumber;
    private String deliveryRequest;
    private Long totalOrderAmount;
    private String address;
    private String zipCode;
    private Long amount;
    private Integer trackingNo;
    private LocalDate hopeDate;
}
