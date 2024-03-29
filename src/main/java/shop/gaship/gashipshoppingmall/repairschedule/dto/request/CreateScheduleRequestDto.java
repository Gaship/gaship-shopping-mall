package shop.gaship.gashipshoppingmall.repairschedule.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 수리설치스케줄을 생성하기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@AllArgsConstructor
public class CreateScheduleRequestDto {

    @NotNull(message = "일자를 기입해주세요")
    private LocalDate date;
    @Min(value = 1, message = "localNo 는 0 이하일 수 없습니다.")
    @NotNull(message = "지역번호를 기입해주세요")
    private Integer localNo;

    @NotNull(message = "물량을 기입해주세요")
    private Integer labor;

}
