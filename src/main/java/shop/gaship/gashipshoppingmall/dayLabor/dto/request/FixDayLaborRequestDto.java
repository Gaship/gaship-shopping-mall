package shop.gaship.gashipshoppingmall.dayLabor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 지역별 물량을 수정하기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FixDayLaborRequestDto {
    @Min(1)
    @NotNull
    private Integer localNo;
    @Min(0)
    @NotNull
    private Integer maxLabor;
}
