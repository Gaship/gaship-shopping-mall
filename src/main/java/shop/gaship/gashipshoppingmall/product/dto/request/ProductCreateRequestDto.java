package shop.gaship.gashipshoppingmall.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 상품 생성 요청 dto 클래스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {
    private Integer categoryNo;

    private Integer deliveryTypeNo;

    private String name;

    private Long amount;

    private String manufacturer;

    private String manufacturerCountry;

    private String seller;

    private String importer;

    private Long shippingInstallationCost;

    private String qualityAssuranceStandard;

    private String color;

    private Integer stockQuantity;

    private String explanation;

    private List<Integer> tagNos;

    private String code;

}
