package shop.gaship.gashipshoppingmall.productreview.event;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 상품평 delete 이벤트입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@RequiredArgsConstructor
public class ProductReviewDeleteEvent {
    @Getter
    private final List<String> imagePaths;
}
