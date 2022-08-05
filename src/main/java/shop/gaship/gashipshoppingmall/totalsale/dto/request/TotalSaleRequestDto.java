package shop.gaship.gashipshoppingmall.totalsale.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 일자를 통해 총 매출을 입력받기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@AllArgsConstructor
public class TotalSaleRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
}