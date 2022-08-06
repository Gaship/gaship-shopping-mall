package shop.gaship.gashipshoppingmall.daylabor.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @NotNull(message = "지역번호를 입력하세요")
    private Integer localNo;
    @Min(0)
    @NotNull(message = "최대물량을 입력하세요")
    private Integer maxLabor;
}
