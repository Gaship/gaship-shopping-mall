package shop.gaship.gashipshoppingmall.employee.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.exception
 * fileName       : EmployeeNotFoundException
 * author         : 유호철
 * date           : 2022/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
 */
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException() {
        super("직원이 존재하지 않습니다.");
    }
}
