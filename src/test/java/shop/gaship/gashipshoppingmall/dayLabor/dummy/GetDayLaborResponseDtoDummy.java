package shop.gaship.gashipshoppingmall.dayLabor.dummy;

import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.dummy
 * fileName       : GetDayLaborResponseDtoDummy
 * author         : 유호철
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초 생성
 */

public class GetDayLaborResponseDtoDummy {
    private GetDayLaborResponseDtoDummy() {

    }

    public static GetDayLaborResponseDto dummy1() {
        return new GetDayLaborResponseDto("부산", 10);
    }

    public static GetDayLaborResponseDto dummy2() {
        return new GetDayLaborResponseDto("서울", 20);
    }
}
