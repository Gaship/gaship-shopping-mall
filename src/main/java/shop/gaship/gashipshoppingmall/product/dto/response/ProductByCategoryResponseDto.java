package shop.gaship.gashipshoppingmall.product.dto.response;

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
public class ProductByCategoryResponseDto {
    private Integer productNo;
    private String productName;
    private Long productPrice;
    @Setter
    private String filePath;

    public ProductByCategoryResponseDto(Integer productNo,
                                        String productName, Long productPrice) {
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
