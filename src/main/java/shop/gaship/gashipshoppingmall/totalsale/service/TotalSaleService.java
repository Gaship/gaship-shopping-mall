package shop.gaship.gashipshoppingmall.totalsale.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;

/**
 * 매출현황을 보기위한 interface 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface TotalSaleService {


    /**
     * 검색할 시작날와 끝날짜를 입력하면 관련 매출현황이 나오는 메서드입니다.
     *
     * @param dto 시작날짜와 끝날짜가 기입되어있습니다.
     * @return 매출현황이 반환됩니다.
     */
    List<TotalSaleResponseDto> findTotalSaleList(TotalSaleRequestDto dto);
}
