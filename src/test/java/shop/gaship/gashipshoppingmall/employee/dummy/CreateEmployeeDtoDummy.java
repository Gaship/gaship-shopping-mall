package shop.gaship.gashipshoppingmall.employee.dummy;

import shop.gaship.gashipshoppingmall.employee.dto.CreateEmployeeDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.dummy
 * fileName       : CreateEmployeeDtoDummy
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초 생성
 */

public class CreateEmployeeDtoDummy {
    private CreateEmployeeDtoDummy() {

    }

    public static CreateEmployeeDto dummy(){
        return new CreateEmployeeDto(1, 1, "test", "test@naver.com", "test","010101");
    }
}
