package shop.gaship.gashipshoppingmall.statuscode.exception;

/**
 * 찾는 상태코드가 없는 경우 발생하는 exception.
 *
 * @author : 김세미
 * @since 1.0
 */
public class StatusCodeNotFoundException extends RuntimeException {
    public static final String MESSAGE = "해당 상태 코드를 찾을 수 없습니다.";

    public StatusCodeNotFoundException() {
        super(MESSAGE);
    }
}
