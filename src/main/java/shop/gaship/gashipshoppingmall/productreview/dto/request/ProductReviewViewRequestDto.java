package shop.gaship.gashipshoppingmall.productreview.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 상품평 조회 시 요청 dto입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@Builder
public class ProductReviewViewRequestDto {
    @NotNull(message = "orderProductNo 는 필수 입력값입니다.")
    @Min(value = 1, message = "주문상품번호는 0보다 커야합니다.")
    private Integer orderProductNo;

    @NotNull(message = "productNo 는 필수 입력값입니다.")
    @Min(value = 1, message = "상품번호는 0보다 커야합니다.")
    private Integer productNo;

    @NotNull(message = "memberNo 는 필수 입력값입니다.")
    @Min(value = 1, message = "회원번호는 0보다 커야합니다.")
    private Integer memberNo;

    @Builder.Default
    private Pageable pageable = PageRequest.of(0, 5);
}
