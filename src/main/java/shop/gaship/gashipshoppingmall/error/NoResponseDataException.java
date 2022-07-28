package shop.gaship.gashipshoppingmall.error;

/**
 * 응답 데이터가 존재하지않을 경우의 예외입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
public class NoResponseDataException extends RuntimeException {
    public NoResponseDataException(String message) {
        super(message);
    }
}
