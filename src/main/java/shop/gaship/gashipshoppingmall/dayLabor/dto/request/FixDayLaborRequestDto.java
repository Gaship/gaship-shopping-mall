package shop.gaship.gashipshoppingmall.dayLabor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.dto.request
 * fileName       : FixDayLaborRequestDto
 * author         : 유호철
 * date           : 2022/07/13
 * description    : ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초생성
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FixDayLaborRequestDto {
    private Integer localNo;
    private Integer maxLabor;
}
