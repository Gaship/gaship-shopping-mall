package shop.gaship.gashipshoppingmall.dataprotection.exception;

/**
 * 디코딩(복호화)가 실패하였을 때 발생하는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class DecodeFailureException extends RuntimeException {
    public DecodeFailureException(String message) {
        super(message);
    }
}
