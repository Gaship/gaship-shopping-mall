package shop.gaship.gashipshoppingmall.employee.exception;

/**
 * 직원이 존재하지않을경우 보여주는 클래스입니다.
 *
 * @see RuntimeException
 * @author : 유호철
 * @since 1.0
 */
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException() {
        super("직원이 존재하지 않습니다.");
    }
}
