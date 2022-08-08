package shop.gaship.gashipshoppingmall.product.event;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.product.entity.Product;

/**
 * 상품 생성 및 수정 이벤트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
public class ProductSaveUpdateEvent {
    private final List<String> imageLinks;

    @Setter
    private Product savedProduct;

    private final List<CommonFile> beforeImages = new ArrayList<>();

    public ProductSaveUpdateEvent(List<String> imageLinks,
                                  Product savedProduct) {
        this.imageLinks = imageLinks;
        this.savedProduct = savedProduct;
    }

    public void updateBeforeImages(List<CommonFile> images) {
        beforeImages.addAll(images);
    }
}
