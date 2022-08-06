package shop.gaship.gashipshoppingmall.repairschedule.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 스케줄에대한 정보를 수정하기위한 정보를 담고있는 클래스입니다.
 *
 * @author 유호철
 * @since 1.0
 */

@Getter
public class ModifyScheduleRequestDto {
    @NotNull(message = "물량을 기입해주세요")
    private Integer labor;

    @NotNull(message = "일자를 기입해주세요")
    private LocalDate date;
    @Min(1)
    @NotNull(message = "지역 번호를 기입해주세요ㅕ")
    private Integer localNo;
}
