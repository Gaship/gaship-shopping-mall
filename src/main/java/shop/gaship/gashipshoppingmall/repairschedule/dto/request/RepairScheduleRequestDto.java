package shop.gaship.gashipshoppingmall.repairschedule.dto.request;

import java.time.LocalDate;
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
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
}
