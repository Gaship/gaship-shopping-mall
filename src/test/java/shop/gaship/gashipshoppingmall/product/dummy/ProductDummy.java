package shop.gaship.gashipshoppingmall.product.dummy;

import java.time.LocalDateTime;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;

/**
 * packageName    : shop.gaship.gashipshoppingmall.product.dummy
 * fileName       : ProductDummy
 * author         : 김보민
 * date           : 2022-07-11
 * description    : 상품 더미
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-11        김보민       최초 생성
 */
public class ProductDummy {

    /**
     * methodName : dummy
     * author : 김보민
     * description : 상품 더미
     *
     * @return product
     */
    public static Product dummy() {
        return Product.builder()
                .category(CategoryDummy.dummy())
                .name("상품")
                .amount(10000L)
                .registerDatetime(LocalDateTime.now())
                .manufacturer("제조사")
                .manufacturerCountry("제조국")
                .seller("판매자")
                .importer("수입자")
                .shippingInstallationCost(0L)
                .qualityAssuranceStandard("품질보증기준")
                .color("#FFFFFF")
                .stockQuantity(10)
                .imageLink1("이미지링크")
                .build();
    }
}
