package shop.gaship.gashipshoppingmall.orderproduct.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
public class OrderProductDetailResponseDto {
    private Integer productNo;
    private Integer orderNo;
    private String productName;
    private Long totalOrderAmount;
    private String orderProductStatus;
    private String trackingNo;
    private String color;
    private String manufacturer;
    private String manufacturerCountry;
    private String seller;
    private String importer;
    private String qualityAssuranceStandard;
    private String explanation;

    @Setter
    private String filePath;

    public OrderProductDetailResponseDto(Integer productNo,
                                         Integer orderNo, String productName,
                                         Long totalOrderAmount, String orderProductStatus,
                                         String trackingNo, String color, String manufacturer,
                                         String manufacturerCountry, String seller,
                                         String importer, String qualityAssuranceStandard,
                                         String explanation) {
        this.productNo = productNo;
        this.orderNo = orderNo;
        this.productName = productName;
        this.totalOrderAmount = totalOrderAmount;
        this.orderProductStatus = orderProductStatus;
        this.trackingNo = trackingNo;
        this.color = color;
        this.manufacturer = manufacturer;
        this.manufacturerCountry = manufacturerCountry;
        this.seller = seller;
        this.importer = importer;
        this.qualityAssuranceStandard = qualityAssuranceStandard;
        this.explanation = explanation;
    }
}
