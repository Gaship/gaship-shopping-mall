package shop.gaship.gashipshoppingmall.dayLabor.dummy;

import shop.gaship.gashipshoppingmall.dayLabor.dto.request.FixDayLaborRequestDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.dummy
 * fileName       : FixDayLaborRequestDtoDummy
 * author         : 유호철
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초 생성
 */
public class FixDayLaborRequestDtoDummy {

    private FixDayLaborRequestDtoDummy() {

    }

    public static FixDayLaborRequestDto dummy() {
        return new FixDayLaborRequestDto(1, 22);
    }

}
