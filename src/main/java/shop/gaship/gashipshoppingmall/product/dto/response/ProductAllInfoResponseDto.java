package shop.gaship.gashipshoppingmall.product.dto.response;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 상품을 가져올 정보들이 담겨있습니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
public class ProductAllInfoResponseDto {
    private Integer productNo;
    private String productName;
    private String productCode;
    private String categoryName;
    private Long amount;
    private LocalDateTime dateTime;
    private String manufacturer;
    private String country;
    private String seller;
    private String importer;
    private Long installationCost;
    private String quality;
    private String color;
    private Integer quantity;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String explanation;
    private Integer level;
    private String upperName;

    private List<String> tags = new ArrayList<>();

    public ProductAllInfoResponseDto(Integer productNo, String productName, String productCode,
                                     String categoryName, Long amount, LocalDateTime dateTime,
                                     String manufacturer, String country, String seller,
                                     String importer, Long installationCost, String quality,
                                     String color, Integer quantity, String img1,
                                     String img2, String img3, String img4,
                                     String img5, String explanation, Integer level,
                                     String upperName) {
        this.productNo = productNo;
        this.productName = productName;
        this.productCode = productCode;
        this.categoryName = categoryName;
        this.amount = amount;
        this.dateTime = dateTime;
        this.manufacturer = manufacturer;
        this.country = country;
        this.seller = seller;
        this.importer = importer;
        this.installationCost = installationCost;
        this.quality = quality;
        this.color = color;
        this.quantity = quantity;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
        this.explanation = explanation;
        this.level = level;
        this.upperName = upperName;
    }
}
