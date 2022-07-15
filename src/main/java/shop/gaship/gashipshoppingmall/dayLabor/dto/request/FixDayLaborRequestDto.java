package shop.gaship.gashipshoppingmall.dayLabor.dto.request;

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
    private Integer localNo;
    private Integer maxLabor;
}
