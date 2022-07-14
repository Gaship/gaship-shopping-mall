package shop.gaship.gashipshoppingmall.repairSchedule.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dto.request
 * fileName       : SchedulePageRequestDto
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SchedulePageRequestDto {
    @NotNull
    private Integer page;
    
    @NotNull
    private Integer size;
}
