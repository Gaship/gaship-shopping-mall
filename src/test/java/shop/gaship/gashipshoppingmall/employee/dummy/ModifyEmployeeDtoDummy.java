package shop.gaship.gashipshoppingmall.employee.dummy;

import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.dummy
 * fileName       : ModifyEmployeeDtoDummy
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초 생성
 */

public class ModifyEmployeeDtoDummy {
    private ModifyEmployeeDtoDummy() {

    }

    public static ModifyEmployeeRequestDto dummy() {
        return new ModifyEmployeeRequestDto(1, "test", "10000");
    }
}
