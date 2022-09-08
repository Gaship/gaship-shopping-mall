package shop.gaship.gashipshoppingmall.totalsale.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 일자를 통해 총 매출을 입력받기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
public class TotalSaleRequestDto {
    @NotNull(message = "startDate 는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "endDate 는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

}