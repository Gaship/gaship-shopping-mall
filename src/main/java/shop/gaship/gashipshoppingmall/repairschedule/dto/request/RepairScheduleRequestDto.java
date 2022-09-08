package shop.gaship.gashipshoppingmall.repairschedule.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 날짜를 통해 조회하기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepairScheduleRequestDto {
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "startDate 는 필수 입력값입니다.")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "endDate 는 필수 입력값입니다.")
    private LocalDate endDate;
}
