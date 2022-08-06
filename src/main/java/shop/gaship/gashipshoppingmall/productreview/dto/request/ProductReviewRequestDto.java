package shop.gaship.gashipshoppingmall.productreview.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/**
 * 상품평 등록, 수정 시 요청 dto입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@Builder
public class ProductReviewRequestDto {
    @Min(value = 1, message = "주문상품번호(상품평번호)는 0보다 커야합니다.")
    @NotNull(message = "주문상품번호(상품평번호)는 필수 입력값입니다.")
    private Integer orderProductNo;

    @Size(max = 100, message = "상품평 제목은 100자를 넘을 수 없습니다.")
    private String title;

    private String content;

    @Min(value = 1, message = "별점은 1이상 5이하여야 합니다.")
    @Max(value = 5, message = "별점은 1이상 5이하여야 합니다.")
    @NotNull
    private Integer starScore;
}
