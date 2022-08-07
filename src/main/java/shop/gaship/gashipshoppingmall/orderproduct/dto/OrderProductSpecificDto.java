package shop.gaship.gashipshoppingmall.orderproduct.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @Min(value = 0, message = "추가 보증기간을 다시 확인해주세요.")
    private Integer additionalWarrantyPeriod;

    @Min(value = 0, message = "적용하는 쿠폰 보증기간 개월 수를 확인해주세요.")
    private Integer couponWarrantyPeriod;

    @Min(value = 0, message = "올바르지 못한 쿠폰입니다.")
    private Integer couponNo;

    @NotNull(message = "쿠폰 사용 여부를 확인해주세요.")
    private Boolean isUseCoupon;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hopeDate;
}
