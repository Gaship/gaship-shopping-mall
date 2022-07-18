package shop.gaship.gashipshoppingmall.repairSchedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Min;
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

    @NotNull(message = "일자를 기입해주세요")
    private LocalDate date;
    @Min(1)
    @NotNull(message = "지역번호를 기입해주세요")
    private Integer localNo;

    @NotNull(message = "물량을 기입해주세요")
    private Integer labor;

}
