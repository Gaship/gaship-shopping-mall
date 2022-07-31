package shop.gaship.gashipshoppingmall.addresslocal.dummy;

import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.daylabor.dummy.DayLaboyDummy;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.dummy
 * fileName       : AddressLocalDummt
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초 생성
 */
public class AddressLocalDummy {
    private AddressLocalDummy() {
    }

    public static AddressLocal dummy1() {
        return new AddressLocal("부산광역시", 1, true, null, null);
    }

    public static AddressLocal dummy2() {
        return new AddressLocal("마산턱별시", 2, true, null, null);
    }

    public static AddressLocal dummy3() {
        return new AddressLocal("그냥시", 2, true, null, null);
    }

    public static AddressLocal dummy4() {
        return new AddressLocal("그냥시", 2, true, DayLaboyDummy.dummy1(), null);
    }
}
