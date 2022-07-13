package shop.gaship.gashipshoppingmall.dayLabor.repository.custom;

import java.util.List;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;

/**
 *packageName     : shop.gaship.gashipshoppingmall.dayLabor.repository.custom
 * fileName       : DayLaborRepositoryCustom
 * author         : 유호철
 * date           : 2022/07/13
 * description    : 전체 조회를 위한 QueryDsl 쓰기위한 interface
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초 생성
 */

public interface DayLaborRepositoryCustom {
    List<GetDayLaborResponseDto> findAllDayLabor();
}
