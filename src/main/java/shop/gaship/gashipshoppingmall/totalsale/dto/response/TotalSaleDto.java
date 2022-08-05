package shop.gaship.gashipshoppingmall.totalsale.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 판매총량이 나오게 되는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@AllArgsConstructor
public class TotalSaleDto {
    private LocalDateTime totalSaleDate;
    private Long orderCnt;

    private Long orderCancelCnt;

    private Long orderSaleCnt;
    private Long totalAmount;

    private Integer cancelAmount;

    private Long orderSaleAmount;
}
