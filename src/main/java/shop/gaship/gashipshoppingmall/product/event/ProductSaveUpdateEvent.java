package shop.gaship.gashipshoppingmall.product.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shop.gaship.gashipshoppingmall.product.entity.Product;

/**
 * 상품 생성 및 수정 이벤트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ProductSaveUpdateEvent {
    private final List<String> imageLinks;

    @Setter
    private Product savedProduct;
}
