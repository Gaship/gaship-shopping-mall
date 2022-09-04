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
    @Min(value = 1, message = "localNo 는 0 이하일 수 없습니다.")
    @NotNull(message = "지역번호를 입력하세요")
    private Integer localNo;
    @Min(value = 0, message = "maxLabor 는 0 미만일 수 없습니다.")
    @NotNull(message = "최대물량을 입력하세요")
    private Integer maxLabor;
}
