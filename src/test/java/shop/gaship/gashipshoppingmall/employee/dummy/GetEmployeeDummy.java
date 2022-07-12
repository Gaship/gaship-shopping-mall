package shop.gaship.gashipshoppingmall.employee.dummy;

import shop.gaship.gashipshoppingmall.employee.dto.GetEmployee;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.dummy fileName       : GetEmployeeDummy
 * author         : 유호철 date           : 2022/07/12 description    :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2022/07/12        유호철       최초 생성
 */
public class GetEmployeeDummy {

    private GetEmployeeDummy() {
    }

    public static GetEmployee dummy() {
        return new GetEmployee("a", "a@naver.com", "01");
    }

    public static GetEmployee dummy2() {
        return new GetEmployee("b", "b@naver.com", "01");
    }
}
