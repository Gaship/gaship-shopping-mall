package shop.gaship.gashipshoppingmall.daylabor.repository.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.daylabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.util.PageResponse;

/**
 * QueryDsl 을 쓰기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@NoRepositoryBean
public interface DayLaborRepositoryCustom {
    /**
     * 전체 지역별물량을 조회하기위한 메서드입니다.
     *
     * @param pageable 페이지 정보가 입려됩니다.
     * @return list 조회된 지역별물량들이 반환됩니다.
     * @author 유호철
     */
    PageResponse<GetDayLaborResponseDto> findAllDayLabor(Pageable pageable);
}
