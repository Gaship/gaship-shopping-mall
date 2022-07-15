package shop.gaship.gashipshoppingmall.repairSchedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * page 정보를 담고있는 클래스입니다.
 *
 *
 * @author : 유호철
 * @since 1.0
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
