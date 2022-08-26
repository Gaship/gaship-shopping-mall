package shop.gaship.gashipshoppingmall.employee.dummy;

import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.dummy fileName       : GetEmployeeDummy
 * author         : 유호철 date           : 2022/07/12 description    :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2022/07/12        유호철       최초 생성
 */
public class GetEmployeeDummy {

    private GetEmployeeDummy() {
    }

    public static EmployeeInfoResponseDto dummy() {
        return new EmployeeInfoResponseDto("a", "a@naver.com", "01", "부산", 1);
    }

    public static EmployeeInfoResponseDto dummy2() {
        return new EmployeeInfoResponseDto("b", "b@naver.com", "01", "부산", 1);
    }
}
