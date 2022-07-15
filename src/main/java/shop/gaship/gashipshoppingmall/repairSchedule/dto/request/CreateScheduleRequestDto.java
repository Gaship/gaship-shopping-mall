package shop.gaship.gashipshoppingmall.repairSchedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 수리설치스케줄을 생성하기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
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
