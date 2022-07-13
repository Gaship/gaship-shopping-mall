package shop.gaship.gashipshoppingmall.dayLabor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.dto.response
 * fileName       : GetDayLaborResponseDto
 * author         : 유호철
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초생성
 */

@Getter
@AllArgsConstructor
public class GetDayLaborResponseDto {
    private String local;

    private Integer maxLabor;
}
