package shop.gaship.gashipshoppingmall.dataprotection.exception;


/**
 * 인코딩(암호화)가 실패하였을 때 발생하는 예외입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
public class EncodeFailureException extends RuntimeException {
    public EncodeFailureException(String message) {
        super(message);
    }
}
