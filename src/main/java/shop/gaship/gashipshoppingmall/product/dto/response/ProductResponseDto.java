package shop.gaship.gashipshoppingmall.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 상품정보 조회시 반환될 상품정보입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto {
    @NotNull
    private Integer no;
    @NotNull
    private String name;

    @NotNull
    private Long amount;

    @NotNull
    private String manufacturer;

    @NotNull
    private String manufacturerCountry;

    @NotNull
    private String seller;

    @NotNull
    private String importer;

    @NotNull
    private Long shippingInstallationCost;

    @NotNull
    private String qualityAssuranceStandard;

    @NotNull
    private String color;

    @NotNull
    private Integer stockQuantity;

    @NotNull
    private String imageLink1;

    private String imageLink2;

    private String imageLink3;

    private String imageLink4;

    private String imageLink5;

    @NotNull
    private String explanation;

    @NotNull
    private String code;

    @NotNull
    LocalDateTime registerDatetime;

    @Builder
    public ProductResponseDto(Integer no, String name, Long amount,
                              String manufacturer, String manufacturerCountry,
                              String seller, String importer,
                              Long shippingInstallationCost,
                              String qualityAssuranceStandard,
                              String color, Integer stockQuantity,
                              String imageLink1, String explanation,
                              String code,
                              LocalDateTime registerDatetime) {
        this.no = no;
        this.name = name;
        this.amount = amount;
        this.manufacturer = manufacturer;
        this.manufacturerCountry = manufacturerCountry;
        this.seller = seller;
        this.importer = importer;
        this.shippingInstallationCost = shippingInstallationCost;
        this.qualityAssuranceStandard = qualityAssuranceStandard;
        this.color = color;
        this.stockQuantity = stockQuantity;
        this.imageLink1 = imageLink1;
        this.explanation = explanation;
        this.code = code;
        this.registerDatetime = registerDatetime;
    }
}
