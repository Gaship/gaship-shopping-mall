package shop.gaship.gashipshoppingmall.product.dto.request;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;



/**
 * 상품들을 조회할때 조건들이 들어있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    @Builder.Default
    private Integer productNo = 0;

    @Builder.Default
    private String productName = null;

    @Builder.Default
    private String code = null;

    @Builder.Default
    private Integer categoryNo = 0;

    @Builder.Default
    private Long minAmount = 0L;

    @Builder.Default
    private Long maxAmount = 0L;

    @Builder.Default
    private Integer tagNo = 0;

    @NotNull
    @Builder.Default
    private Pageable pageable = PageRequest.of(0, 10);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductRequestDto that = (ProductRequestDto) o;
        return Objects.equals(productNo, that.productNo)
                && Objects.equals(productName, that.productName)
                && Objects.equals(code, that.code)
                && Objects.equals(categoryNo, that.categoryNo)
                && Objects.equals(minAmount, that.minAmount)
                && Objects.equals(maxAmount, that.maxAmount)
                && Objects.equals(tagNo, that.tagNo)
                && Objects.equals(pageable, that.pageable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productNo, productName,
                code, categoryNo, minAmount, maxAmount,
                tagNo, pageable);
    }
}
