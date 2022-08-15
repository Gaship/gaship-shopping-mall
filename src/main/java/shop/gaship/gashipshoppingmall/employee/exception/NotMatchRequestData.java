package shop.gaship.gashipshoppingmall.employee.exception;

/**
 * 요청정보가 서로 일치하지않을때 던지는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class NotMatchRequestData extends RuntimeException {
    public NotMatchRequestData(String message) {
        super(message);
    }
}
