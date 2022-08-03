package shop.gaship.gashipshoppingmall.order.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import lombok.Getter;

/**
 * 주문할 상품 정보 명세 dto 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
public class OrderProductSpecificDto {
    @Min(value = 0, message = "올바르지 못한 상품입니다.")
    private Integer productNo;

    @Min(value = 0, message = "올바르지 못한 쿠폰입니다.")
    private Integer couponNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hopeDate;
}
