package shop.gaship.gashipshoppingmall.product.dummy;

import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;

import java.time.LocalDateTime;

/**
 * 더미객체
 *
 * @author : 유호철
 * @since 1.0
 */
public class ProductResponseDtoDummy {
    private ProductResponseDtoDummy (){

    }

    public static  ProductResponseDto dummy(){
        return ProductResponseDto.builder()
                .no(1)
                .name("이름")
                .amount(1000L)
                .manufacturer("ㅇ")
                .manufacturerCountry("중꿔")
                .seller("나")
                .importer("ㅇ")
                .shippingInstallationCost(1L)
                .qualityAssuranceStandard("ㅇ")
                .color("푸릉생")
                .stockQuantity(1)
                .imageLink1("이미지")
                .explanation("설명")
                .productCode("code")
                .registerDatetime(LocalDateTime.now())
                .build();
    }

}
