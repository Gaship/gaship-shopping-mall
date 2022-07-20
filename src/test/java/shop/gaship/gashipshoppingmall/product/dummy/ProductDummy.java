package shop.gaship.gashipshoppingmall.product.dummy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;

public class ProductDummy {

    public static Product dummy() {
        Product dummy =  Product.builder()
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
                .explanation("설명")
                .code("A001")
                .build();
        dummy.updateImageLinks(List.of("이미지 링크"));
        return dummy;
    }

    public static ProductCreateRequestDto createRequestDummy() {
        return new ProductCreateRequestDto(
                1,
                1,
                "상품이름",
                1000L,
                "생산자",
                "생산국",
                "판매자",
                "수입자",
                0L,
                "품질보증기준",
                "#000000",
                10,
                "설명",
                List.of(1),
                "A001"
        );
    }

    public static ProductModifyRequestDto modifyRequestDummy() {
        return new ProductModifyRequestDto(
                1,
                1,
                1,
                "수정 상품이름",
                2000L,
                "수정 생산자",
                "수정 생산국",
                "수정 판매자",
                "수정 수입자",
                0L,
                "수정 품질보증기준",
                "#000000",
                20,
                "수정 설명",
                List.of(),
                "A001"
        );
    }

    public static Product dummy2() {
        return Product.builder()
                .no(null)
                .category(CategoryDummy.bottomDummy())
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
                .code("code")
                .productTags(new ArrayList<>())
                .build();
    }
}
