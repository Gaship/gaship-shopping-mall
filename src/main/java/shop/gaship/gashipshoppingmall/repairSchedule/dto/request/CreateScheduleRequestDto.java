package shop.gaship.gashipshoppingmall.repairSchedule.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dto.request
 * fileName       : CreateScheduleRequestDto
 * author         : 유호철
 * date           : 2022/07/14
 * description    : 직원스케줄을 생성을 위한 클래스
 * ===========================================================
 * DATE         AUTHOR      NOTE
 * -----------------------------------------------------------
 * 2022/07/14   유호철       최초 생성
 */

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CreateScheduleRequestDto {

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer localNo;

    @NotNull
    private Integer labor;

}
