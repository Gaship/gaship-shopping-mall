package shop.gaship.gashipshoppingmall.product.dto.request;

import java.util.ArrayList;
import java.util.List;
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
public class ProductRequestViewDto {
    @Builder.Default
    private Integer productNo = 0;

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

    @Builder.Default
    private String statusName = null;

    @Builder.Default
    private List<Integer> productNoList = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductRequestViewDto that = (ProductRequestViewDto) o;
        return Objects.equals(productNo, that.productNo)
            && Objects.equals(categoryNo, that.categoryNo)
            && Objects.equals(minAmount, that.minAmount)
            && Objects.equals(maxAmount, that.maxAmount)
            && Objects.equals(tagNo, that.tagNo)
            && Objects.equals(pageable, that.pageable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productNo,
            categoryNo, minAmount, maxAmount,
            tagNo, pageable);
    }
}
