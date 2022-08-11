package shop.gaship.gashipshoppingmall.employee.dummy;

import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.dummy fileName       : EmployeeDummy
 * author         : 유호철 date           : 2022/07/12 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/12        유호철       최초
 * 생성
 */
public class EmployeeDummy {
    private EmployeeDummy() {
    }

    public static Employee dummy() {
        Employee dummy = new Employee();
        ReflectionTestUtils.setField(dummy, "statusCode", null);
        ReflectionTestUtils.setField(dummy, "addressLocal", null);
        ReflectionTestUtils.setField(dummy, "name", "잠온다");
        ReflectionTestUtils.setField(dummy, "email", "test@naver.com");
        ReflectionTestUtils.setField(dummy, "password", "password");
        ReflectionTestUtils.setField(dummy, "phoneNo", "01011111111");
        ReflectionTestUtils.setField(dummy, "encodedEmailForSearch", "test@naver.com");

        return dummy;
    }

}
