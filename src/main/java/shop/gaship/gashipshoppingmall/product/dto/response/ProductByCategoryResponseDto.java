package shop.gaship.gashipshoppingmall.product.dto.response;

import java.util.List;
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
    private Long amount;
    @Setter
    private List<String> filePaths;

    public ProductByCategoryResponseDto(Integer productNo,
                                        String productName, Long productPrice) {
        this.productNo = productNo;
        this.productName = productName;
        this.amount = productPrice;
    }
}
