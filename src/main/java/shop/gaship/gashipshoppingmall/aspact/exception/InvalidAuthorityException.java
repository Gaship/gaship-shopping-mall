package shop.gaship.gashipshoppingmall.aspact.exception;

/**
 * 올바른 권한으로 접근하지 않았을 때
 *
 * @author 김민수
 * @since 1.0
 */
public class InvalidAuthorityException extends RuntimeException {
    public InvalidAuthorityException(String message) {
        super(message);
    }
}
