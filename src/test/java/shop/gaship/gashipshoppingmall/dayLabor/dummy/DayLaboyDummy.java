package shop.gaship.gashipshoppingmall.dayLabor.dummy;

import shop.gaship.gashipshoppingmall.config.dayLabor.entity.DayLabor;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.dummy
 * fileName       : DayLaboyDummy
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초생성
 */
public class DayLaboyDummy {
    private DayLaboyDummy() {
    }

    public static DayLabor dummy1() {
        return new DayLabor(null, 10);
    }

    public static DayLabor dummy2() {
        return new DayLabor(null, 20);
    }
}
