package shop.gaship.gashipshoppingmall.repairSchedule.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
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
    private final Integer labor;

    @NotNull(message = "일자를 기입해주세요")
    private final LocalDate date;
    @Min(1)
    @NotNull(message = "지역 번호를 기입해주세요ㅕ")
    private final Integer localNo;

    @Builder
    public ModifyScheduleRequestDto(Integer labor, LocalDate date, Integer localNo) {
        this.labor = labor;
        this.date = date;
        this.localNo = localNo;
    }
}
