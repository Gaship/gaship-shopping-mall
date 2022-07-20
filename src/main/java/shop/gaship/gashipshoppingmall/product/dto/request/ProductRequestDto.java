package shop.gaship.gashipshoppingmall.product.dto.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

/**
 * 상품들을 조회할때 조건들이 들어있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductRequestDto {
    private Integer productNo = 0;
    private String productName = null;
    private String code = null;
    private Integer categoryNo = 0;
    private Long minAmount = 0L;
    private Long maxAmount = 0L;
    private Integer tagNo = 0;
    @NotNull
    private Pageable pageable = PageRequest.of(0, 10);


    @Builder
    public ProductRequestDto(Integer productNo, String productName,
                             String productCode, Integer categoryNo,
                             Long minAmount, Long maxAmount,
                             Integer tagNo, Pageable pageable) {
        this.productNo = productNo;
        this.productName = productName;
        this.code = productCode;
        this.categoryNo = categoryNo;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.tagNo = tagNo;
        this.pageable = pageable;
    }
}
