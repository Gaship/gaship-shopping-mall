package shop.gaship.gashipshoppingmall.repairSchedule.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 *packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dto.request
 * fileName       : ModifySheduleRequestDto
 * author         : 유호철
 * date           : 2022/07/14
 * description    : 수정을 위한 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */

@Getter
public class ModifyScheduleRequestDto {

    @NotNull
    private Integer labor;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer localNo;

    @Builder
    public ModifyScheduleRequestDto(Integer labor, LocalDate date, Integer localNo) {
        this.labor = labor;
        this.date = date;
        this.localNo = localNo;
    }
}
