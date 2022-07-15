package shop.gaship.gashipshoppingmall.member.exception;

/**
 * 회원가입 요청이 거부되었을 때 발생하는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class SignUpDenyException extends RuntimeException {
    public SignUpDenyException(String message) {
        super(message);
    }
}
