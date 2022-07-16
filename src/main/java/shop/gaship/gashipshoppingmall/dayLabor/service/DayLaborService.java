package shop.gaship.gashipshoppingmall.dayLabor.service;

import shop.gaship.gashipshoppingmall.dayLabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.FixDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;

import java.util.List;

/**
 * 지역별 물량을 서비스레이어에 사용하기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface DayLaborService {

    /**
     * 지역별 물량을 생성하기위한 메서드입니다.
     *
     * @param dto  지역별물량을 생성하기위한 지역번호와 최대물량이 담겨져있습니다.
     * @author 유호철
     */
    void addDayLabor(CreateDayLaborRequestDto dto);

    /**
     * 지역별물량을 수정하기위한 메서드입니다.
     *
     * @param fixDto 찾아야할 주소정보와 수정할 최대물량에대한 정보가 들어있습니다.
     * @author 유호철
     */
    void modifyDayLabor(FixDayLaborRequestDto fixDto);

    /**
     * 모든지역별물량을 조회하기위한 메서드입니다.
     *
     * @return list 조회한 지역별물량에대한 정보들이 담겨있습니다.
     * @author 유호철
     */
    List<GetDayLaborResponseDto> findDayLabors();
}
