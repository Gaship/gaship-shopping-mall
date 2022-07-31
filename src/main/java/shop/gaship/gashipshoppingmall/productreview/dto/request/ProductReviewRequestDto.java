package shop.gaship.gashipshoppingmall.productreview.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Min(1)
    private Integer orderProductNo;

    private String title;

    private String content;

    @Min(1)
    @Max(5)
    private Integer starScore;
}
