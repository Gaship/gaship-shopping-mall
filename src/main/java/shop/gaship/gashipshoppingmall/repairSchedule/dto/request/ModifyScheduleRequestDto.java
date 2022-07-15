package shop.gaship.gashipshoppingmall.repairSchedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dto.request
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
