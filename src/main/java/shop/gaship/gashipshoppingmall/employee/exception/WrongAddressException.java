package shop.gaship.gashipshoppingmall.employee.exception;

/**
 * 주소지가 잘못되었을경우 사용되는 클래스입니다.
 *
 * @see RuntimeException
 * @author : 유호철
 * @since 1.0
 */
public class WrongAddressException extends RuntimeException {

    public WrongAddressException() {
        super("주소가 잘못기입되었습니다");
    }
}
