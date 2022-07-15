package shop.gaship.gashipshoppingmall.employee.dummy;

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
        return new Employee(null, null, "잠온다", "test@naver.com", "password",
                "01011111111");
    }

}
