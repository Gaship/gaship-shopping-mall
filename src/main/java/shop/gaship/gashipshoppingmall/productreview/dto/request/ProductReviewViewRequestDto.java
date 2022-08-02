package shop.gaship.gashipshoppingmall.productreview.dto.request;

import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
@Builder
public class ProductReviewViewRequestDto {
    @Min(0)
    @Builder.Default
    private Integer orderProductNo = 0;

    @Min(0)
    @Builder.Default
    private Integer productNo = 0;

    @Min(0)
    @Builder.Default
    private Integer memberNo = 0;

    @Builder.Default
    private Pageable pageable = PageRequest.of(0, 5);
}
