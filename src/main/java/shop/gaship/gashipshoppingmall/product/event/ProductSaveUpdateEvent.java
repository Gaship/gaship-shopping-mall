package shop.gaship.gashipshoppingmall.product.event;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 상품 생성 및 수정 이벤트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@RequiredArgsConstructor
public class ProductSaveUpdateEvent {
    @Getter
    private final List<String> imageLinks;
}
