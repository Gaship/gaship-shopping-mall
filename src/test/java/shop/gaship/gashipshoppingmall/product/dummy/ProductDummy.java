package shop.gaship.gashipshoppingmall.product.dummy;

import java.time.LocalDateTime;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

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

    public static Product dummy2(){
        return Product.builder()
                .no(null)
                .category(CategoryDummy.upperDummy())
                .salesStatus(StatusCodeDummy.dummy())
                .deliveryType(StatusCodeDummy.dummy())
                .name("이름")
                .amount(1000L)
                .registerDatetime(LocalDateTime.now())
                .manufacturer("ㅇㅇ")
                .manufacturerCountry("ㅇㅇ")
                .seller("가")
                .importer("나")
                .shippingInstallationCost(1L)
                .qualityAssuranceStandard("다")
                .color("빨강")
                .stockQuantity(1)
                .imageLink1("라")
                .explanation("설명")
                .productCode("code")
                .build();
    }
}
