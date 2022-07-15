package shop.gaship.gashipshoppingmall.dayLabor.repository.custom;

import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;

import java.util.List;

/**
 * QueryDsl 을 쓰기위한 인터페이스입니다.
 *
 *
 * @author : 유호철
 * @since 1.0
 */

public interface DayLaborRepositoryCustom {
    /**
     * 전체 지역별물량을 조회하기위한 메서드입니다.
     *
     * @return list 조회된 지역별물량들이 반환됩니다.
     * @author 유호철
     */
    List<GetDayLaborResponseDto> findAllDayLabor();
}
