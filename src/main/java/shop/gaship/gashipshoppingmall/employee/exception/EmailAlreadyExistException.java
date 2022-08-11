package shop.gaship.gashipshoppingmall.employee.exception;

/**
 * 이메일이 이미존재했을경우 발생하는 예외입니다.
 *
 * @author : 유호철
 * @see RuntimeException
 * @since 1.0
 */
public class EmailAlreadyExistException extends RuntimeException {
    public static final String MESSAGE = "이미 사용중인 이메일입니다.";

    public EmailAlreadyExistException() {
        super(MESSAGE);
    }
}
