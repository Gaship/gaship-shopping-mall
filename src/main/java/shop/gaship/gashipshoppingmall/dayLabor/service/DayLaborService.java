package shop.gaship.gashipshoppingmall.dayLabor.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.FixDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.service fileName       : DayLaborService
 * author         : 유호철 date           : 2022/07/12 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/12        유호철       최초
 * 생성
 */
public interface DayLaborService {

    /**
     * methodName : createDayLabor
     * author : 유호철
     * description : 지역별 생산량을 생성하기위한 클래스
     *
     * @param dto CreateDayLaborRequestDto
     */
    void createDayLabor(CreateDayLaborRequestDto dto);

    void modifyDayLabor(FixDayLaborRequestDto fixDto);

    List<GetDayLaborResponseDto> getAllDayLabors();
}
