package shop.gaship.gashipshoppingmall.employee.exception;

/**
 * 잘못된 공통코드가 주입되었을때 사용되는 클래스입니다.
 *
 * @see RuntimeException
 * @author : 유호철
 * @since 1.0
 */
public class WrongStatusCodeException extends RuntimeException {

    public WrongStatusCodeException() {
        super("상태 코드 값이 잘못 기입되었습니다.");
    }
}
