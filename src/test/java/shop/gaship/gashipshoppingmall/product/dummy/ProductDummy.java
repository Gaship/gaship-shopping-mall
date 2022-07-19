package shop.gaship.gashipshoppingmall.product.dummy;

import java.time.LocalDateTime;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;

public class ProductDummy {

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
