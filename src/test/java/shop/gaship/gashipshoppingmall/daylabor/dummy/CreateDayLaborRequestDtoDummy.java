package shop.gaship.gashipshoppingmall.daylabor.dummy;

import shop.gaship.gashipshoppingmall.daylabor.dto.request.CreateDayLaborRequestDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.dummy
 * fileName       : CreateDayLaborRequestDtoDummy
 * author         : 유호철
 * date           : 2022/07/13
 * description    : 지역별 물량 생성시 Dto Dummy
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초 생성
 */

public class CreateDayLaborRequestDtoDummy {

    private CreateDayLaborRequestDtoDummy() {

    }

    public static CreateDayLaborRequestDto dummy() {
        return new CreateDayLaborRequestDto(1, 10);
    }

}
