package shop.gaship.gashipshoppingmall.repairSchedule.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 스케줄에대한 정보를 수정하기위한 정보를 담고있는 클래스입니다.
 *
 *
 * @author 유호철
 * @since 1.0
 */

@Getter
public class ModifyScheduleRequestDto {

    @NotNull
    private final Integer labor;

    @NotNull
    private final LocalDate date;

    @NotNull
    private final Integer localNo;

    @Builder
    public ModifyScheduleRequestDto(Integer labor, LocalDate date, Integer localNo) {
        this.labor = labor;
        this.date = date;
        this.localNo = localNo;
    }
}
