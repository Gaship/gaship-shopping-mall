package shop.gaship.gashipshoppingmall.productreview.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 상품평 존재여부 확인 응답 dto입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ProductReviewExistsResponseDto {
    private Boolean isExist;
}
