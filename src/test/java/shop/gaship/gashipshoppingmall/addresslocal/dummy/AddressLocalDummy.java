package shop.gaship.gashipshoppingmall.addresslocal.dummy;

import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;

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
        AddressLocal dummy = new AddressLocal();
        ReflectionTestUtils.setField(dummy, "addressName", "부산광역시");
        ReflectionTestUtils.setField(dummy, "level", 1);
        ReflectionTestUtils.setField(dummy, "allowDelivery", true);
        ReflectionTestUtils.setField(dummy, "dayLabor", null);
        ReflectionTestUtils.setField(dummy, "upperLocal", null);
        return dummy;
    }

    public static AddressLocal dummy2() {
        AddressLocal dummy = new AddressLocal();
        ReflectionTestUtils.setField(dummy, "addressName", "마산턱별시");
        ReflectionTestUtils.setField(dummy, "level", 2);
        ReflectionTestUtils.setField(dummy, "allowDelivery", true);
        ReflectionTestUtils.setField(dummy, "dayLabor", null);
        ReflectionTestUtils.setField(dummy, "upperLocal", null);
        return dummy;
    }

    public static AddressLocal dummy3() {
        AddressLocal dummy = new AddressLocal();
        ReflectionTestUtils.setField(dummy, "addressName", "그냥시");
        ReflectionTestUtils.setField(dummy, "level", 2);
        ReflectionTestUtils.setField(dummy, "allowDelivery", true);
        ReflectionTestUtils.setField(dummy, "dayLabor", null);
        ReflectionTestUtils.setField(dummy, "upperLocal", null);
        return dummy;
    }
}
