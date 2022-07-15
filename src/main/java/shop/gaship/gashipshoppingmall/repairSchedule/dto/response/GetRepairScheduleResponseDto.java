package shop.gaship.gashipshoppingmall.repairSchedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dto.response
 * fileName       : GetRepairScheduleResponseDto
 * author         : 유호철
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class GetRepairScheduleResponseDto {
    private String localName;

    private LocalDate localDate;

    private Integer labor;

    @Builder
    public GetRepairScheduleResponseDto(String localName, LocalDate localDate, Integer labor) {
        this.localName = localName;
        this.localDate = localDate;
        this.labor = labor;
    }
}
